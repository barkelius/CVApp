package test.model.game.impl.chess.pieces.impl;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.BoardMap;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.BishopPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.PawnPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.RookPiece;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by barke on 2016-03-27.
 */
public class PieceTest {

    Board boardInterface = new BoardMap();

    @Before
    public void testing() throws Exception {
        boardInterface = new BoardMap();
    }

    //Do more cases
    @Test
    public void testRookMoves() throws Exception {
        Position rookPos = Position.of(0, 0);
        ChessPiece rook = boardInterface.get(rookPos);
        assertEquals(0, rook.validMoves(boardInterface, rookPos, false).size());

        Map<Position, AbstractChessPiece> testBoard = new HashMap<>();
        rookPos = Position.of(2, 0);
        testBoard.put(rookPos, RookPiece.white());
        testBoard.put(Position.of(1, 0), PawnPiece.black());
        testBoard.put(Position.of(2, 2), PawnPiece.black());
        testBoard.put(Position.of(5, 0), PawnPiece.white());
        testBoard.put(Position.of(6, 0), PawnPiece.black());

        rook = testBoard.get(rookPos);
        Board tmp = new BoardMap(testBoard);
        assertEquals(5, rook.validMoves(tmp, rookPos, false).size());

    }

    //Do more cases
    @Test
    public void testBishopMoves() throws Exception {
        Position bishopPos = Position.of(2, 0);
        ChessPiece bishop = boardInterface.get(bishopPos);
        assertEquals(0, bishop.validMoves(boardInterface, bishopPos, false).size());

        Map<Position, AbstractChessPiece> testBoard = new HashMap<>();
        bishopPos = Position.of(2, 0);
        testBoard.put(bishopPos, BishopPiece.white());
        testBoard.put(Position.of(1, 0), PawnPiece.black());
        testBoard.put(Position.of(1, 1), PawnPiece.black());
        testBoard.put(Position.of(5, 3), PawnPiece.black());
        Board tmp = new BoardMap(testBoard);
        bishop = tmp.get(bishopPos);
        //tmp.print();
        assertEquals(4, bishop.validMoves(tmp, bishopPos, false).size());

    }

    //Done
    @Test
    public void testPawnMoves() throws Exception {
        Position pos = Position.of(2, 1);
        ChessPiece pawn = boardInterface.get(pos);
        assertEquals(2, pawn.validMoves(boardInterface, pos, false).size());

        Map<Position, AbstractChessPiece> testBoard = new HashMap<>();
        pos = Position.of(2, 0);
        testBoard.put(pos, BishopPiece.white());
        testBoard.put(Position.of(1, 1), PawnPiece.black());
        testBoard.put(Position.of(3, 1), PawnPiece.black());
        testBoard.put(Position.of(2, 1), PawnPiece.white());
        Board tmp = new BoardMap(testBoard);
        pawn = tmp.get(pos);
        assertEquals(2, pawn.validMoves(tmp, pos, false).size());

        pos = Position.of(7,7);
        testBoard.put(pos, PawnPiece.white());
        pawn = tmp.get(pos);
        assertEquals(0, pawn.validMoves(tmp, pos, false).size());
    }



}
