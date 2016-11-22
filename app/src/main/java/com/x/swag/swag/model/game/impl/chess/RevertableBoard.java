package com.x.swag.swag.model.game.impl.chess;

import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;

/**
 * Created by barke on 2016-06-12.
 */
public class RevertableBoard extends BoardMap {

   //RevertState revertState = null;

    public static RevertableBoard wrap(BoardMap b){
        return new RevertableBoard(b);
    }

    private RevertableBoard(BoardMap b){
        super(b.pieces);
    }
    private int move = 0;
  //  @Override
  //  public AbstractChessPiece move(Position from, Position to) {
    //    revertState = new RevertState(get(to), from, to);
  //      return super.move(from, to);
  //  }
    public RevertState revertableMove(Position from, Position to){
        ++move;
        AbstractChessPiece toPiece = pieces.get(to);
        ChessPiece removed = super.move(from, to);
        if(toPiece != removed)
            System.out.println("ALDGJBHNSOLGFJHSNDLGJ");
        RevertState state = new RevertState(toPiece, from, to);
        System.out.println("Move:"+move + ".. " + state.toString());
        return state;
    }

   // public RevertState getRevertState() {
   //     return revertState;
   // }

    public void revert(RevertState state) {
        System.out.println("Revert:"+move + ".. " + state.toString());
        --move;
        AbstractChessPiece moved = pieces.remove(state.to);
        pieces.put(state.from, moved);
        if(state.removed instanceof AbstractChessPiece)
            pieces.put(state.to, (AbstractChessPiece) state.removed);
    }

    public class RevertState {
        public ChessPiece removed;
        public Position from;
        public Position to;

        public RevertState(ChessPiece removed, Position from, Position to) {
            this.removed = removed;
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "Removed:" + removed +", from:" + from + ", to:"+to;
        }
    }
}
