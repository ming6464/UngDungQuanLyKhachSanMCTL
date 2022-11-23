package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ming6464.ungdungquanlykhachsanmctl.Fragment.LoaiPhongFragment;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.SoDoPhongFragment;

public class ViewFragment extends FragmentStateAdapter {
    public ViewFragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new SoDoPhongFragment();
            case 1: return new LoaiPhongFragment();
        }
        return new SoDoPhongFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
