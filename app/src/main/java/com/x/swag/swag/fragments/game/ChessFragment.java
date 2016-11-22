package com.x.swag.swag.fragments.game;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.x.swag.swag.R;
import com.x.swag.swag.adapters.ChessAdapter;
import com.x.swag.swag.model.game.TurnGame;
import com.x.swag.swag.model.game.ai.AI;
import com.x.swag.swag.model.game.ai.HasAI;
import com.x.swag.swag.model.game.data.GameState;
import com.x.swag.swag.model.game.impl.chess.ChessGame;
import com.x.swag.swag.model.game.impl.chess.Position;
import com.x.swag.swag.model.game.impl.chess.pieces.AbstractChessPiece;
import com.x.swag.swag.model.game.options.HasDifficulty;
import com.x.swag.swag.util.log.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameListener} interface
 * to handle interaction events.
 * Use the {@link ChessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChessFragment extends AbstractGameFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ChessGame game;

    private GameListener mListener;
    private ChessAdapter mChessAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChessFragment newInstance(String param1, String param2) {
        ChessFragment fragment = new ChessFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ChessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        TurnGame.TakeTurn callback = new TurnGame.TakeTurn() {
            @Override public void takeTurn(boolean outcome, GameState updated) {
                TextView turnValue = (TextView)getView().findViewById(R.id.turnValueTV);
                turnValue.setText(String.valueOf(game.getTurn()));
                Log.d("ChessGame TakeTurn", "Took turn: populating removed pieces");
                populateRemovedPieces();
            }
        };



        ChessGame.ChessGamePresenter presenter = new ChessGame.ChessGamePresenter() {
            private long start = 0;
            @Override public void aiThinking() {
                start = System.currentTimeMillis();
                final ProgressBar aiProgress = (ProgressBar)getView().findViewById(R.id.ai_progress);
                aiProgress.setVisibility(View.VISIBLE);
                TextView time = (TextView)getView().findViewById(R.id.ai_time);
                time.setText("");
            }
            @Override public void aiDone() {
                final ProgressBar aiProgress = (ProgressBar)getView().findViewById(R.id.ai_progress);
                aiProgress.setVisibility(View.INVISIBLE);
                TextView time = (TextView)getView().findViewById(R.id.ai_time);
                time.setText(String.valueOf((System.currentTimeMillis()-start)) + "ms");
            }
        };

        game = ChessGame.initializeClean(callback, difficulty, presenter);
        game.setAiTurnHandler(new AI.AITurnHandler() {
            @Override public GameState played() {
                mChessAdapter.notifyDataSetChanged();

                populateRemovedPieces();

                return null;
            }
        });
    }

    private void populateRemovedPieces() {
        LinearLayout whitePieces = (LinearLayout)getView().findViewById(R.id.white_pieces);
        whitePieces.removeAllViews();
        for (AbstractChessPiece whitePiece : game.getRemovedPieces(true)) {
            Integer id = ChessAdapter.mPieces[whitePiece.value()-1];
            whitePieces.addView(createRemovedChessIcon(id));
        }

        LinearLayout blackPieces = (LinearLayout)getView().findViewById(R.id.black_pieces);
        blackPieces.removeAllViews();
        for (AbstractChessPiece blackPiece : game.getRemovedPieces(false)) {
            Integer id = ChessAdapter.mPieces[blackPiece.value()+5];
            blackPieces.addView(createRemovedChessIcon(id));
        }
    }

    @NonNull
    private ImageView createRemovedChessIcon(Integer id) {
        ImageView icon = new ImageView(getActivity());
        icon.setImageResource(id);
        //icon.setAdjustViewBounds(true);
        icon.setMaxHeight(100);
        icon.setMaxWidth(100);
        return icon;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_chess_frament, container, false);
        GridView grid = (GridView)inflate.findViewById(R.id.board);
        mChessAdapter = new ChessAdapter(getActivity(), game);
        grid.setAdapter(mChessAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                boolean update = game.clicked(Position.from(position));
                if (update)
                    mChessAdapter.notifyDataSetChanged();
            }
        });

        TextView turnValue = (TextView)inflate.findViewById(R.id.turnValueTV);
        turnValue.setText(String.valueOf(game.getTurn()));
        return inflate;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (GameListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " + GameListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
