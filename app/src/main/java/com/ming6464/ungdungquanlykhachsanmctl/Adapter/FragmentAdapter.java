package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    private Fragment[] list_frag;
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity, Fragment [] list) {
        super(fragmentActivity);
        this.list_frag = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list_frag[position];
    }

    @Override
    public int getItemCount() {
        return list_frag.length;
    }
}

