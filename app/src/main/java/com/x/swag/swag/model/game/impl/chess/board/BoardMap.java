package com.x.swag.swag.model.game.impl.chess.board;

import static com.x.swag.swag.model.game.impl.chess.ChessGame.width;

import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.PieceSerializationAdapter;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.BishopPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.EmptyPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KingPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KnightPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.OutOfBoardPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.PawnPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.QueenPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.RookPiece;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by barke on 2016-03-25.
 */
public class BoardMap implements Board {

    protected final Map<Position, AbstractChessPiece> pieces;

    private static Map<Position, AbstractChessPiece> initializeBoard() {
        final Map<Position, AbstractChessPiece> board = Maps.newHashMap();

        int row = 0, col = 0;
        board.put(Position.of(col++, row), RookPiece.white());
        board.put(Position.of(col++, row), KnightPiece.white());
        board.put(Position.of(col++, row), BishopPiece.white());
        board.put(Position.of(col++, row), KingPiece.white());
        board.put(Position.of(col++, row), QueenPiece.white());
        board.put(Position.of(col++, row), BishopPiece.white());
        board.put(Position.of(col++, row), KnightPiece.white());
        board.put(Position.of(col++, row), RookPiece.white());

        row = 1;
        for(col = 0; col < width; ++col)
            board.put(Position.of(col, row), PawnPiece.white());

        row = 6;
        for(col = 0; col < width; ++col)
            board.put(Position.of(col, row), PawnPiece.black());

        row = 7; col = 0;
        board.put(Position.of(col++, row), RookPiece.black());
        board.put(Position.of(col++, row), KnightPiece.black());
        board.put(Position.of(col++, row), BishopPiece.black());
        board.put(Position.of(col++, row), QueenPiece.black());
        board.put(Position.of(col++, row), KingPiece.black());
        board.put(Position.of(col++, row), BishopPiece.black());
        board.put(Position.of(col++, row), KnightPiece.black());
        board.put(Position.of(col++, row), RookPiece.black());

        return board;
    }

    public BoardMap(){
        pieces = initializeBoard();
    }

    public BoardMap(Map<Position, AbstractChessPiece> pieces){
        this.pieces = pieces;
    }

    @Override
    public ChessPiece get(Position position) {
        if(!position.isValid())
            return new OutOfBoardPiece();

        AbstractChessPiece piece = pieces.get(position);
        if(piece == null)
            return new EmptyPiece();
        return piece;
    }

    /**
     *  Move a piece from position "From" to position "To".
     *
     * @param from
     * @param to
     * @return the moved piece or null if none was moved
     */
    @Override
    public AbstractChessPiece move(Position from, Position to){
        if(!to.isValid())
            return null;
        AbstractChessPiece moved = pieces.remove(from);
        if(moved != null)
            return pieces.put(to, moved);
        return null;
    }


    public void print(){
        System.out.println("BoardMap: -----");

        for(int y = 0; y < width; ++y){
            String output = "";
            for(int x = 0; x < width; ++x){
                AbstractChessPiece piece = pieces.get(Position.of(x, y));
                if(piece == null)
                    output += "0 ";
                else
                    output += piece.value() + " ";
            }
            System.out.println(output);
        }
        System.out.println("BoardMap: -----");
    }

   /* @Override
    public King findKing(boolean white){
        for (Map.Entry<Position, AbstractChessPiece> entry : pieces.entrySet()) {
            if(entry.getValue().isWhite() == white && entry.getValue() instanceof KingPiece)
                return new King(entry.getKey(), (KingPiece)entry.getValue());
        }
        throw new IllegalStateException("No " + (white ? "white":"black") + " king on the board");
    }*/

    @Override
    public Iterable<Map.Entry<Position, AbstractChessPiece>> entries(){
        return pieces.entrySet();
    }

    @Override
    public Board clone(){
        return new BoardMap(Maps.newHashMap(pieces));
    }

    public Board concurrentClone(){
        ConcurrentMap<Position, AbstractChessPiece> clone = new MapMaker().concurrencyLevel(4).initialCapacity(pieces.size()).makeMap();
        clone.putAll(pieces);
        return new BoardMap(clone);
    }

    public String serialize(){
        Gson gson = new GsonBuilder().registerTypeAdapter(AbstractChessPiece.class, new PieceSerializationAdapter()).create();
        return gson.toJson(this);
    }

    public Board unserialize(String json){
        Gson gson = new GsonBuilder().registerTypeAdapter(AbstractChessPiece.class, new PieceSerializationAdapter()).create();
        Map<Position, AbstractChessPiece> m = gson.fromJson(json, HashMap.class);
        System.out.println(m.toString());
        return new BoardMap(m);
    }


}
