package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.UserAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.User;
import com.ming6464.ungdungquanlykhachsanmctl.R;
import com.ming6464.ungdungquanlykhachsanmctl.UpdateUserActivity;
import com.ming6464.ungdungquanlykhachsanmctl.UserDB;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KhachHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KhachHangFragment extends Fragment {
    private static final int MY_REQUEST_CODE = 10;
    public RecyclerView rcvUser;

    private UserAdapter userAdapter;
    private List<User> mListUser;

    public static KhachHangFragment newInstance() {
        KhachHangFragment fragment = new KhachHangFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_khach_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvUser = view.findViewById(R.id.rcv_user);
        userAdapter = new UserAdapter(new UserAdapter.IClickItemUser() {
            @Override
            public void updateUser(User user) {
                clickUpdateUser(user);
            }

            @Override
            public void deleteUser(User user) {
                clickDeleteUser(user);
            }
        });

        mListUser = new ArrayList<>();
        userAdapter.setData(mListUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.setAdapter(userAdapter);
        loatData();
    }

    private void clickDeleteUser(User user) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xac nhan xoa nguoi dung")
                .setMessage("Ban co chac chan muon xoa khong?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete User
                        UserDB.getInstance(getContext()).userDAO().DeleteUser(user);
                        Toast.makeText(getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                        loatData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickUpdateUser(User user) {
        Intent intent = new Intent(getContext(), UpdateUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_user", user);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    private void loatData() {
        mListUser = UserDB.getInstance(getContext()).userDAO().getListUser();
        userAdapter.setData(mListUser);
    }

    public boolean isUserExist(User user) {
        List<User> list = UserDB.getInstance(getContext()).userDAO().checkUser(user.getUsername());
        return list != null && !list.isEmpty();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loatData();
        }
    }
}