package com.x.swag.swag.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.x.swag.swag.R;
import com.x.swag.swag.model.game.impl.chess.ChessGame;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.ChessPiece;

/**
 * Created by barke on 2016-03-28.
 */
public class ChessAdapter extends BaseAdapter {
    private Context mContext;

    private ChessGame game;

    public ChessAdapter(Context c, ChessGame game) {
        mContext = c;
        this.game = game;
    }

    public int getCount() {
        return 64;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(135, 135));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageDrawable(null);
        Position pos = Position.from(position);

        ChessPiece piece = game.getPiece(pos);

        if ( piece.isValid() && !piece.isEmpty()){
            AbstractChessPiece cp = (AbstractChessPiece)piece;
            Integer id = mPieces[(cp.value()-1 + (cp.getTeam() == -1 ? 6 : 0))];
            imageView.setImageResource(id);
        }

        if (piece.equals(game.getHighlightedPiece()))
            imageView.setBackgroundColor(Color.GREEN);
        else if (game.getValidMoves().contains(pos))
            imageView.setBackgroundColor(piece instanceof AbstractChessPiece ? Color.RED : Color.YELLOW);
        else
            imageView.setBackgroundColor((position % 2 == ((position / 8) % 2) ? Color.rgb(0x20, 0x20, 0x20) : Color.WHITE));
        if(game.getCheckedPositions().contains(pos))
            imageView.setBackgroundColor(Color.MAGENTA);
        return imageView;
    }

    // references to our images
    public static Integer[] mPieces = {
            R.drawable.plt60,
            R.drawable.nlt60,
            R.drawable.chess_blt60,
            R.drawable.rlt60,
            R.drawable.qlt60,
            R.drawable.chess_klt60,
            R.drawable.pdt60,
            R.drawable.ndt60,
            R.drawable.chess_bdt60,
            R.drawable.rdt60,
            R.drawable.qdt60,
            R.drawable.chess_kdt60,
    };
}

