package com.x.swag.swag.model.game.impl.chess.pieces;

import com.x.swag.swag.model.game.impl.chess.board.Board;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KingPiece;

import java.util.List;

/**
 * Created by barke on 2016-03-25.
 */
public abstract class AbstractChessPiece implements ChessPiece {

    private final static int BLACK = -1;
    private final static int WHITE = 1;
    protected final int team;

    protected AbstractChessPiece(final boolean white){
        this.team = white ? WHITE : BLACK;
    }

    protected final boolean isEnemy(AbstractChessPiece piece){
        return team != piece.getTeam();
    }

    public final int getTeam() {
        return team;
    }

    public final boolean isWhite(){
        return team == WHITE;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final boolean isValid() {
        return true;
    }


    protected void addIfEnemy(List<Position> positions, Board board, Position position, boolean includeKing){
        if (!position.isValid())
            return;

        ChessPiece piece = board.get(position);
        if (( !piece.isValid() ) || ( piece.isEmpty() ) || (!includeKing && ( piece instanceof KingPiece )))
            return;
        else if ( isEnemy((AbstractChessPiece)piece) )
            positions.add(position);
    }

    protected void addIfEnemy(List<Position> positions, Board board, Position position){
        addIfEnemy(positions, board, position, false);
    }

    protected void addIfEmpty(List<Position> positions, Board board, Position position){
        if (!position.isValid())
            return;

        ChessPiece piece = board.get(position);
        if (piece.isEmpty())
            positions.add(position);
    }
    protected void addIfEnemyOrEmpty(List<Position> positions, Board board, Position position){
        addIfEnemyOrEmpty(positions, board, position, false);
    }
    protected void addIfEnemyOrEmpty(List<Position> positions, Board board, Position position, boolean includeKing){
        final int initialSize = positions.size();

        addIfEmpty(positions, board, position);
        if(initialSize == positions.size())
            addIfEnemy(positions, board, position, includeKing);
    }


    public abstract int value();


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "team=" + team + '}';
    }


}
