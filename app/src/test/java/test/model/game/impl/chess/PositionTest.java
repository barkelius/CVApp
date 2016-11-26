package test.model.game.impl.chess;

import com.x.swag.swag.model.game.impl.chess.Position;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by barke on 2016-03-25.
 */
public class PositionTest {

    @Test
    public void testPos() throws Exception {
        Position p = Position.of(0, 1);
        assertEquals(p.getX(), 0);
        assertEquals(p.getY(), 1);

        //InvalidPos
        p = Position.of(7, 7);
        assertNotEquals(p, p.moveY(1));
        assertNotEquals(p, Position.of(10, 10));
    }

    @Test
    public void testGetX() throws Exception {
        Position p = Position.of(0, 1);
        assertEquals(p.getX(), 0);
    }

    @Test
    public void testGetY() throws Exception {
        Position p = Position.of(0, 1);
        assertEquals(p.getY(), 1);
    }

    @Test
    public void testRandomMovement(){
        Position p = Position.of(4, 4);
        assertEquals(p, p.moveTopLeft().moveBottomRight());
        assertEquals(p, p.moveTopRight().moveBottomLeft());
    }

    @Test
    public void testMoveTopLeft() throws Exception {
        Position p = Position.of(3, 3);
        Position movedPos = p.moveTopLeft();
        assertNotEquals(p, movedPos);
        assertEquals(p.getY() - 1, movedPos.getY());
        assertEquals(p.getX() - 1, movedPos.getX());
    }

    @Test
    public void testMoveTopRight() throws Exception {
        Position p = Position.of(3, 3);
        Position movedPos = p.moveTopRight();
        assertNotEquals(p, movedPos);
        assertEquals(p.getY() - 1, movedPos.getY());
        assertEquals(p.getX() + 1, movedPos.getX());
    }

    @Test
    public void testMoveBottomLeft() throws Exception {
        Position p = Position.of(3, 3);
        Position movedPos = p.moveBottomLeft();
        assertNotEquals(p, movedPos);
        assertEquals(p.getY() + 1, movedPos.getY());
        assertEquals(p.getX() - 1, movedPos.getX());
    }

    @Test
    public void testMoveBottomRight() throws Exception {
        Position p = Position.of(3, 3);
        Position movedPos = p.moveBottomRight();
        assertNotEquals(p, movedPos);
        assertEquals(p.getY() + 1, movedPos.getY());
        assertEquals(p.getX() + 1, movedPos.getX());
    }

    @Test
    public void testMoveX() throws Exception {
        Position p = Position.of(4, 4);
        assertEquals(p, p.moveX(1).moveX(-1));
        assertEquals(p.getX()+1, p.moveX(1).getX());
        assertEquals(p.getX()-1, p.moveX(-1).getX());
        assertEquals(p.getX(), p.moveX(1).moveX(1).moveX(-2).getX());
    }

    @Test
    public void testMoveY() throws Exception {
        Position p = Position.of(4, 4);
        assertEquals(p, p.moveY(1).moveY(-1));
        assertEquals(p.getY()+1, p.moveY(1).getY());
        assertEquals(p.getY()-1, p.moveY(-1).getY());
        assertEquals(p.getY(), p.moveY(1).moveY(1).moveY(-2).getY());
    }

    @Test
    public void testMoveLeft() throws Exception {
        Position p = Position.of(4, 4);
        assertEquals(p.getX()-1, p.moveLeft(-1).getX());
        assertEquals(p.getX()-1, p.moveLeft(1).getX());
    }

    @Test
    public void testMoveRight() throws Exception {
        Position p = Position.of(4, 4);
        assertEquals(p.getX()+1, p.moveRight(-1).getX());
        assertEquals(p.getX()+1, p.moveRight(1).getX());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(Position.of(2, 2), Position.of(2, 2));
        assertNotEquals(Position.of(3, 3), Position.of(2, 2));

        assertNotEquals(Position.of(0, 1), Position.invalid());
    }
}