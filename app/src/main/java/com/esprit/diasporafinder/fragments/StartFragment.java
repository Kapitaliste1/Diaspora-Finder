package com.esprit.diasporafinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.diasporafinder.R;

public class StartFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    public void onBackPressed()
    {
        FragmentManager fragmentManager2 = getFragmentManager();
        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
        NewsFeedFragment fragment2 = new NewsFeedFragment();
        fragmentTransaction2.replace(R.id.linearLayoutBase, fragment2);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();
    }
}
