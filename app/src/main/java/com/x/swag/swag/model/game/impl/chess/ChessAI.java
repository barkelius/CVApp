package com.x.swag.swag.model.game.impl.chess;

import android.os.AsyncTask;
import android.util.Pair;

import com.google.common.collect.Lists;
import com.x.swag.swag.model.game.ai.AI;
import com.x.swag.swag.model.game.data.GameState;
import com.x.swag.swag.model.game.impl.chess.board.Board;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.options.HasDifficulty;
import com.x.swag.swag.util.log.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by barke on 2016-06-08.
 */
public class ChessAI extends AI {

    private int team;
    public ChessAI(HasDifficulty.Difficulty difficulty, int team) {
        super(difficulty);
        this.team = team;
    }

    private interface SubDone {
        void done(FromTo result);
    }

    private void start(final Board board, final AITurnHandler aiTurnHandler, final int depth, final int breadth){
        List<Pair<Position, List<Position>>> validMoves = Lists.newArrayList();
        for(Map.Entry<Position, AbstractChessPiece> entry : board.entries()){
            if(entry.getValue().getTeam() != team)
                continue;
            Position from = entry.getKey();
            validMoves.add(Pair.create(from, entry.getValue().validMoves(board, from, true)));
        }

        final int chunk = validMoves.size()/breadth;
        SubDone handler = new SubDone() {
            List<FromTo> results = Lists.newArrayList();

            @Override
            public void done(FromTo result) {
                results.add(result);
                if(results.size() == breadth){
                    FromTo best = new FromTo(null, null, Integer.MIN_VALUE);
                    for (FromTo res : results) {
                        if (res.score > best.score)
                            best = res;
                    }
                    board.move(best.from, best.to);
                    Logger.log(getClass(), "AI timed:" + (System.currentTimeMillis() - startTime) + "ms");
                    Logger.log(getClass(), "AI cheated with MINMAX: F" + best.from + ", T:" + best.to +", S:" + best.score);
                    aiTurnHandler.played();
                }
            }
        };

        if(difficulty == HasDifficulty.Difficulty.Easy){
            for (Pair<Position, List<Position>> pieceMoves : validMoves) {
                if(pieceMoves.second != null && !pieceMoves.second.isEmpty()){
                    Logger.log(getClass(), "Easy AI difficulty");
                    board.move(pieceMoves.first, pieceMoves.second.iterator().next());
                    aiTurnHandler.played();
                    return;
                }
            }


        }

        for(int i = 0; i < breadth; ++i){
            List<Pair<Position, List<Position>>> subSet = validMoves.subList(chunk * i, ((i + 1) * chunk < validMoves.size() ? (i + 1) * chunk : validMoves.size()));
            new AsyncMaxTask(board.clone(), handler, subSet).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, depth);
        }

    }

    private long startTime;

    public void cheat(Board boardInterface, final AITurnHandler aiTurnHandler){
        //FromTo max = max(board.clone(), 3);
        startTime = System.currentTimeMillis();
        start(boardInterface, new AITurnHandler() {
            @Override public GameState played() {
                aiTurnHandler.played();
                turnHandler.played();
                return null;
            }}, 3, 2);

    }

    @Override
    protected GameState innerProcess(GameState current) {
        return null;
    }


    /**
     * Evaluates the moves
     */
    private double evaluateBoard(Board boardInterface){
        int wMoves = 0, bMoves = 0;
        int wPiecePts = 0, bPiecePts = 0;

        for(Map.Entry<Position, AbstractChessPiece> entry : boardInterface.entries()){
            if(entry.getValue().isWhite()) {
                wMoves += entry.getValue().validMoves(boardInterface, entry.getKey(), true).size();
                wPiecePts += entry.getValue().value();
            } else {
                bMoves += entry.getValue().validMoves(boardInterface, entry.getKey(), true).size();
                bPiecePts += entry.getValue().value();
            }
        }
        return (wPiecePts - bPiecePts) + ((double)(wMoves - bMoves)*0.1);
    }


    private FromTo min(Board boardInterface, int depth){
        if(depth == 0)
            return new FromTo(null, null, -1*evaluateBoard(boardInterface));
        double min = Integer.MAX_VALUE;
        FromTo fromTo = null;
        for(Map.Entry<Position, AbstractChessPiece> entry : boardInterface.entries()){
            if(entry.getValue().getTeam() == team)
                continue;
            Position from = entry.getKey();
            List<Position> validMoves = entry.getValue().validMoves(boardInterface, from, true);

            for(Position to : validMoves) {
                Board clone = boardInterface.clone();
                clone.move(from, to);
                double score = max(clone, depth-1).score;
                if(score < min) {
                    min = score;
                    fromTo = new FromTo(from, to, score);
                }
            }
        }
        return fromTo;
    }

    private FromTo max(Board boardInterface, int depth){
        if(depth == 0)
            return new FromTo(null, null, evaluateBoard(boardInterface));
        double max = Integer.MIN_VALUE;
        FromTo fromTo = null;
        for(Map.Entry<Position, AbstractChessPiece> entry : boardInterface.entries()){
            if(entry.getValue().getTeam() != team)
                continue;
            Position from = entry.getKey();
            List<Position> validMoves = entry.getValue().validMoves(boardInterface, from, true);

            for(Position to : validMoves) {
                Board clone = boardInterface.clone();
                clone.move(from, to);
                double score = min(clone, depth-1).score;
                if(score > max) {
                    max = score;
                    fromTo = new FromTo(from, to, score);
                }
            }
        }
        return fromTo;
    }

    private static class FromTo {
        private Position from;
        private Position to;
        private double score;
        public FromTo(Position from, Position to, double score) {
            this.from = from;
            this.to = to;
            this.score = score;
        }
    }

    private class AsyncMaxTask extends AsyncTask<Integer, Void, FromTo> {
        private Board boardInterface;
        private SubDone handler;
        private List<Pair<Position, List<Position>>> validMoves;

        public AsyncMaxTask(Board boardInterface, SubDone handler, List<Pair<Position, List<Position>>> validMoves) {
            this.boardInterface = boardInterface.clone();
            this.handler = handler;
            this.validMoves = validMoves;
        }

        @Override
        protected FromTo doInBackground(Integer... params) {
            return innerMax(params[0], boardInterface, validMoves);
        }

        @Override
        protected void onPostExecute(FromTo fromTo) {
            handler.done(fromTo);
        }
    }

    private FromTo innerMax(int depth, Board boardInterface, List<Pair<Position, List<Position>>> validMoves){
        double max = Integer.MIN_VALUE;
        FromTo fromTo = null;
        for(Pair<Position, List<Position>> moves : validMoves) {
            Position from = moves.first;
            for(Position to : moves.second) {
                Board clone = boardInterface.clone();
                clone.move(from, to);
                double score = min(clone, depth - 1).score;
                if (score > max) {
                    max = score;
                    fromTo = new FromTo(from, to, score);
                }
            }
        }
        return fromTo;
    }

}
