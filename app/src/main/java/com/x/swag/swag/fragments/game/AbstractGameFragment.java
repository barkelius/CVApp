package com.x.swag.swag.fragments.game;

import android.app.Fragment;
import android.net.Uri;

import com.x.swag.swag.model.game.options.HasDifficulty;

/**
 * Created by barke on 2016-03-27.
 */
public abstract class AbstractGameFragment extends Fragment {

    protected HasDifficulty.Difficulty difficulty;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface GameListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void setDifficulty(HasDifficulty.Difficulty difficulty){
        this.difficulty = difficulty;
    }

    public HasDifficulty.Difficulty getDifficulty() {
        return difficulty;
    }
}
