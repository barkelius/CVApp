package test.util;

import com.x.swag.swag.model.game.impl.chess.Board;
import com.x.swag.swag.model.game.impl.chess.BoardMap;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by barke on 2016-04-09.
 */
public class GsonTest {

    Board boardInterface;
    @Before
    public void setUp() throws Exception {
        boardInterface = new BoardMap();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitialize() throws Exception {
        assertNotNull(boardInterface);
        /*board.print();
        String json = board.serialize();
        System.out.println(json);
        final BoardMap board2 = board.unserialize(json);
        board2.print();
        assertNotNull(board2);
        assertEquals(board2, board);
        */
    }
}
