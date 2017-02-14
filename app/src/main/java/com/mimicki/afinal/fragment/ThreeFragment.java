package com.mimicki.afinal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mimicki.afinal.R;

/**
 * Created by DELL on 7/4/2016.
 */
public class ThreeFragment extends Fragment{
    public static ThreeFragment newInstance() {
        ThreeFragment fragment = new ThreeFragment();
        return fragment;
    }

    public ThreeFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_tf, container, false);
        return rootView;
    }

}
