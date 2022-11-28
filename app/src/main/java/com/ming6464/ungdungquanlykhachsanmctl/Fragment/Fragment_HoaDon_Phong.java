package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ming6464.ungdungquanlykhachsanmctl.R;


public class Fragment_HoaDon_Phong extends Fragment {
    public static Fragment_HoaDon_Phong newInstance() {
        Fragment_HoaDon_Phong fragment = new Fragment_HoaDon_Phong();
        return fragment;
    }

    public Fragment_HoaDon_Phong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__hoa_don__phong, container, false);
        return view;
    }
}