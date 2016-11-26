package com.x.swag.swag.model.game.impl.chess.board;

import com.google.common.collect.Lists;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.BishopPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.EmptyPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KingPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KnightPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.OutOfBoardPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.PawnPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.QueenPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.RookPiece;

import java.util.List;
import java.util.Map;

import static com.x.swag.swag.model.game.impl.chess.ChessGame.width;

/**
 * Created by barke on 2016-06-28.
 */
public class BoardArray implements Board {
    /** [Col][Row] */
    private ChessPiece[][] board;

    private static ChessPiece[][] initializeBoard() {
        final ChessPiece[][] board = new ChessPiece[width][width];

        int row = 0, col = 0;
        board[col++][row] = RookPiece.white();
        board[col++][row] = KnightPiece.white();
        board[col++][row] = BishopPiece.white();
        board[col++][row] = KingPiece.white();
        board[col++][row] = QueenPiece.white();
        board[col++][row] = BishopPiece.white();
        board[col++][row] = KnightPiece.white();
        board[col++][row] = RookPiece.white();

        row = 1;
        for(col = 0; col < width; ++col)
            board[col][row] = PawnPiece.white();

        row = 6;
        for(col = 0; col < width; ++col)
            board[col][row] = PawnPiece.black();

        row = 7; col = 0;
        board[col++][row] = RookPiece.black();
        board[col++][row] = KnightPiece.black();
        board[col++][row] = BishopPiece.black();
        board[col++][row] = QueenPiece.black();
        board[col++][row] = KingPiece.black();
        board[col++][row] = BishopPiece.black();
        board[col++][row] = KnightPiece.black();
        board[col++][row] = RookPiece.black();

        return board;
    }

    public BoardArray(){
        board = initializeBoard();
    }

    public BoardArray(ChessPiece[][] board){
        this.board = board;
    }


    @Override
    public ChessPiece get(Position position) {
        if (!position.isValid())
            return new OutOfBoardPiece();
        ChessPiece piece = board[position.getX()][position.getY()];
        if (piece == null)
            return new EmptyPiece();
        return piece;
    }

    @Override
    public AbstractChessPiece move(Position from, Position to) {
        if (!from.isValid() || !to.isValid())
            return null;
        ChessPiece moved = get(from);
        board[from.getX()][from.getY()] = null;
        if(moved != null){
            ChessPiece replaced = get(to);
            board[to.getX()][to.getY()] = moved;
            return replaced instanceof AbstractChessPiece ? (AbstractChessPiece)replaced : null;
        }
        return null;
    }


    @Override
    public Iterable<Map.Entry<Position, AbstractChessPiece>> entries() {
        List<Map.Entry<Position, AbstractChessPiece>> entries = Lists.newArrayList();
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < width; ++row) {
                final ChessPiece p = board[col][row];
                if(p instanceof AbstractChessPiece) {
                    entries.add(createEntry((AbstractChessPiece)p, col, row));
                }
            }
        }
        return entries;
    }

    private Map.Entry<Position, AbstractChessPiece> createEntry(final AbstractChessPiece p, final int col, final int row) {
        return new Map.Entry<Position, AbstractChessPiece>() {
            @Override
            public Position getKey() {
                return Position.of(col, row);
            }

            @Override
            public AbstractChessPiece getValue() {
                return p;
            }

            @Override
            public AbstractChessPiece setValue(AbstractChessPiece object) {
                throw new UnsupportedOperationException("Immutable");
            }
        };
    }

    @Override
    public Board clone() {
        ChessPiece[][] clone = new ChessPiece[width][width];
        for (int col = 0; col < width; ++col) {
            clone[col] = board[col].clone();
        }
        return new BoardArray(clone);
    }
}
