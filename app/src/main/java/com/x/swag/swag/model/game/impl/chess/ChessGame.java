package com.x.swag.swag.model.game.impl.chess;

import android.util.Log;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.x.swag.swag.model.game.TurnGameImpl;
import com.x.swag.swag.model.game.ai.AI;
import com.x.swag.swag.model.game.ai.HasAI;
import com.x.swag.swag.model.game.data.GameState;
import com.x.swag.swag.model.game.impl.chess.board.Board;
import com.x.swag.swag.model.game.impl.chess.board.BoardArray;
import com.x.swag.swag.model.game.impl.chess.board.BoardMap;
import com.x.swag.swag.model.game.impl.chess.board.BoardUtil;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;
import com.x.swag.swag.model.game.options.HasDifficulty;
import com.x.swag.swag.util.log.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by barke on 2016-03-25.
 * ChessGame implementation.
 */
public class ChessGame extends TurnGameImpl implements HasAI, HasDifficulty {

    public static final int width = 8;
    private Difficulty difficulty;

    private ChessAI ai;
    private Board board;
    private ChessGamePresenter presenter;
    private Position highlightedPos = Position.invalid();

    public interface ChessGamePresenter {
        void aiThinking();
        void aiDone();
    }

    private ChessGame(final TakeTurn callback, Difficulty difficulty, Board board, ChessGamePresenter presenter){
        super(callback);
        this.difficulty = difficulty;
        this.board = board;
        this.presenter = presenter;
        this.ai = new ChessAI(difficulty, -1);
    }

    public static ChessGame initializeClean(final TakeTurn callback, Difficulty difficulty, ChessGamePresenter presenter){
        return new ChessGame(callback, difficulty, new BoardArray(), presenter);
    }

    public List<Position> getValidMoves(){
        return board.get(highlightedPos).validMoves(board, highlightedPos, false);
    }

    public ChessPiece getHighlightedPiece() {
        return board.get(highlightedPos);
    }

    public ChessPiece getPiece(Position pos) {
        return board.get(pos);
    }

    public List<Position> getCheckedPositions(){
        return getCheckedPositions(board);
    }

    private List<Position> getCheckedPositions(Board b){
        BoardUtil.King king = BoardUtil.findKing(b, isWhiteTurn());
        return king.king.checked(b, king.pos);
    }

    private boolean turnOf(boolean color){
        return isWhiteTurn() == color;
    }

    private boolean isWhiteTurn(){
        return turn%2 == 0;
    }

    public boolean clicked(Position clickedPosition) {
        if (highlightedPos.equals(clickedPosition)) {
            highlightedPos = Position.invalid();
        } else if (getValidMoves().contains(clickedPosition)){
            if(move(clickedPosition)) {
                highlightedPos = Position.invalid();
                takeTurn(true, toState(), board);
            }
        } else {
            ChessPiece clickedPiece = board.get(clickedPosition);
            if(clickedPiece.isValid() && !clickedPiece.isEmpty())
                if(turnOf(((AbstractChessPiece) clickedPiece).isWhite()))
                    highlightedPos = clickedPosition;
        }
        return true;
    }

    private void takeTurn(boolean b, GameState gameState, Board board) {
        if(isCheckMate())
            Log.e("ChessGame.move", "CHECK MATE!!!!");
        takeTurn(b, gameState);
        presenter.aiThinking();
        ai.cheat(board, new AI.AITurnHandler() {
            @Override public GameState played() {
                takeTurn(true, null);
                presenter.aiDone();
                return null;
            }
        });
    }

    public List<AbstractChessPiece> getRemovedPieces(final boolean whiteTeam) {

        final Map<Integer, Integer> piecesLeft = Maps.newHashMap();

        final ImmutableList<AbstractChessPiece> remainingPieces = FluentIterable.from(board.entries())
                .filter(new Predicate<Map.Entry<Position, AbstractChessPiece>>() {
                    @Override public boolean apply(Map.Entry<Position, AbstractChessPiece> e) {
                        return e.getValue().isWhite() == whiteTeam;
                    }
                })
                .transform(new Function<Map.Entry<Position, AbstractChessPiece>, AbstractChessPiece>() {
                    @Override public AbstractChessPiece apply(Map.Entry<Position, AbstractChessPiece> e) {
                        return e.getValue();
                    }
                })
                .toList();

        for (AbstractChessPiece p : remainingPieces) {
            Integer currCount = piecesLeft.get(p.value());
            if(currCount == null)
                currCount = 0;
            piecesLeft.put(p.value(), ++currCount);
        }

        Logger.log(getClass(), "Remaining pieces in map: white:"+whiteTeam + ": " + piecesLeft);
        ImmutableList<AbstractChessPiece> removed = FluentIterable.from(new BoardMap().entries())
                .filter(new Predicate<Map.Entry<Position, AbstractChessPiece>>() {
                    @Override public boolean apply(Map.Entry<Position, AbstractChessPiece> e) {
                        return e.getValue().isWhite() == whiteTeam;
                    }
                })
                .filter(new Predicate<Map.Entry<Position, AbstractChessPiece>>() {
                    @Override public boolean apply(final Map.Entry<Position, AbstractChessPiece> e) {
                        Integer count = piecesLeft.get(e.getValue().value());
                        if (count == null || count <= 0)
                            return true;
                        else
                            piecesLeft.put(e.getValue().value(), count-1);
                        return false;
                        /*return !FluentIterable.from(remainingPieces).anyMatch(new Predicate<AbstractChessPiece>() {
                            @Override public boolean apply(AbstractChessPiece p) {
                                return p.value() == e.getValue().value();
                            }
                        });*/
                    }
                })
                .transform(new Function<Map.Entry<Position, AbstractChessPiece>, AbstractChessPiece>() {
                    @Override public AbstractChessPiece apply(Map.Entry<Position, AbstractChessPiece> e) {
                        return e.getValue();
                    }
                })
                .toList();


        Logger.log(getClass(), "Removed for: white:"+whiteTeam + ": " + removed.toString());
        return removed;
    }

   /* @Override
    public void takeTurn(TakeTurn callback, GameState current) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
*/
    /*private void turnTaken(){
        Log.e("ChessGame.turnTaken", "Check mate:" + isCheckMate());
    }
*/
    private GameState toState(){
        String data = "";

        for(Map.Entry<Position, AbstractChessPiece> entry : board.entries())
            data += entry.getKey().getX()+":"+entry.getKey().getY() + "=" + entry.getValue().value() + "\n";

        return GameState.of(getClass(), data);
    }

  /*  private Board fromState(GameState current){
        String data = current.unserialize(getClass());

        return new BoardMap();
    }


    public Bundle toBundle(){
        Bundle b = new Bundle();
        final String ARG_STATE_DATA = "ARG_STATE_DATA";
        b.putString(ARG_STATE_DATA, toState().serialize());
        return b;
    }
*/
    @Override
    public Difficulty getDifficulty() {
        return difficulty;
    }



    public Board simulateMove(Position to){
        Board simulated = board.clone();
        simulated.move(highlightedPos, to);
        return simulated;
    }

    private boolean isCheckMate(){
        List<Map.Entry<Position, AbstractChessPiece>> pieces = FluentIterable.from(board.entries()).filter(new Predicate<Map.Entry<Position, AbstractChessPiece>>() {
            @Override
            public boolean apply(Map.Entry<Position, AbstractChessPiece> e) {
                return turnOf(e.getValue().isWhite());
            }
        }).toList();

        for(Map.Entry<Position, AbstractChessPiece> entry : pieces){
            List<Position> moves = entry.getValue().validMoves(board, entry.getKey(), true);
            for(Position move : moves) {
                if (getCheckedPositions(simulateMove(move)).isEmpty())
                    return false;
            }
        }
        return true;
    }

    private boolean move(Position to){
        if(!getCheckedPositions(simulateMove(to)).isEmpty()) {
            Log.e("ChessGame.move", "YOU ARE CHECKED!!!!");
            return false;
        }
        board.move(highlightedPos, to);
        return true;
    }


    @Override
    public void setAiTurnHandler(AI.AITurnHandler handler) {
        ai.setTurnHandler(handler);
    }
}
