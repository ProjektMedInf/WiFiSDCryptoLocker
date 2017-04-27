package io.github.projektmedinf.wifisdcryptolocker.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class was originally written by matrixxun
 * https://github.com/matrixxun/ProductTour
 */
public class SetupFragment extends Fragment {

    private final static String LAYOUT_ID = "layoutid";

    public static SetupFragment newInstance(int layoutId) {
        SetupFragment pane = new SetupFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return rootView;
    }
}