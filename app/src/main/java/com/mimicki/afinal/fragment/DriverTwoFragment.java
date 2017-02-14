package com.mimicki.afinal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mimicki.afinal.R;

/**
 * Created by DELL on 10/15/2016.
 */
public class DriverTwoFragment extends Fragment {

    TextView user_fname, user_lname, user_gender, user_age, user_no, user_em_no, user_address;

    public static DriverTwoFragment newInstance() {
        DriverTwoFragment fragment = new DriverTwoFragment();
        return fragment;
    }

    public DriverTwoFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.driver_sf, container, false);

        return rootView;
    }


    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        LinearLayout scrollView4 = (LinearLayout) view.findViewById(R.id.scrollView4);
        user_fname = (TextView) view.findViewById(R.id.driver_user_fname);
        user_lname = (TextView) view.findViewById(R.id.driver_user_lname);
        user_gender = (TextView) view.findViewById(R.id.driver_user_gender);
        user_age = (TextView) view.findViewById(R.id.driver_user_age);
        user_no = (TextView) view.findViewById(R.id.driver_user_number);
        user_em_no = (TextView) view.findViewById(R.id.driver_user_em_number);
        user_address = (TextView) view.findViewById(R.id.driver_user_address);

    }

}
