package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.UserAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;
import com.ming6464.ungdungquanlykhachsanmctl.UpdateUserActivity;

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
    private List<People> mListUser;
    private People mUser;

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
        return inflater.inflate(R.layout.fragment_khach_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvUser = view.findViewById(R.id.rcv_user);

        userAdapter = new UserAdapter(new UserAdapter.IClickItemUser() {
            @Override
            public void updateUser(People people) {
                clickUpdateUser(people);
            }

            @Override
            public void deleteUser(People people) {
                clickDeleteUser(people);
            }
        });
        mListUser = new ArrayList<>();
        userAdapter.setData(mListUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.setAdapter(userAdapter);
        loatData();

    }

    private void clickDeleteUser(People people) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xac nhan xoa nguoi dung")
                .setMessage("Ban co chac chan muon xoa khong?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete User
                        KhachSanDB.getInstance(getContext()).getDAO().DeleteUser(people);
                        Toast.makeText(getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                        loatData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickUpdateUser(People people) {
//        Intent intent = new Intent(getContext(), UpdateUserActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("obj_user", people);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, MY_REQUEST_CODE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_user, null);
        EditText edtUsername = view.findViewById(R.id.edt_username);
        EditText edtSex = view.findViewById(R.id.edt_sex);
        EditText edtSDT = view.findViewById(R.id.edt_sdt);
        EditText edtCCCD = view.findViewById(R.id.edt_cccd);
        EditText edtAddress = view.findViewById(R.id.edt_address);
        //set data
        edtUsername.setText(people.getFullName());
        edtSex.setText(people.getSex() + "");
        edtSDT.setText(people.getSDT());
        edtCCCD.setText(people.getCCCD());
        edtAddress.setText(people.getAddress());
        builder.setView(view);
        builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strUsername = edtUsername.getText().toString().trim();
                String strSex = edtSex.getText().toString().trim();
                String strSDT = edtSDT.getText().toString().trim();
                String strCCCD = edtCCCD.getText().toString().trim();
                String strAddress = edtAddress.getText().toString().trim();
                //
//                edtUsername.setText(mUser.getFullName());
//                edtSex.setText(String.valueOf(mUser.getSex()));
//                edtSDT.setText(mUser.getSDT());
//                edtCCCD.setText(mUser.getCCCD());
//                edtAddress.setText(mUser.getAddress());

                //
                people.setFullName(strUsername);
                people.setSex(Integer.parseInt(strSex));
                people.setSDT(strSDT);
                people.setCCCD(strCCCD);
                people.setAddress(strAddress);
                KhachSanDB.getInstance(getContext()).getDAO().UpdateUser(people);
                Toast.makeText(getContext(), "Update Thành Công", Toast.LENGTH_SHORT).show();
                loatData();
            }
        });
        builder.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loatData() {
        mListUser = KhachSanDB.getInstance(getContext()).getDAO().getListUser();
        userAdapter.setData(mListUser);
    }

    public boolean isUserExist(People people) {
        List<People> list = KhachSanDB.getInstance(getContext()).getDAO().checkUser(people.getFullName());
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