package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.UserAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class KhachHangFragment extends Fragment {
    private Spinner sp_status;
    public RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private List<People> mListUser;
    private KhachSanDAO dao;
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
        sp_status = view.findViewById(R.id.fragUser_sp_status);

        userAdapter = new UserAdapter(new UserAdapter.IClickItemUser() {
            @Override
            public void updateUser(People people) {
                clickUpdateUser(people);
            }
        });
        mListUser = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.setAdapter(userAdapter);
        loatData();
        handlerSpinner();

    }

    private void handlerSpinner() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Tất cả");
        statusList.add("Bình Thường");
        statusList.add("Đặt Trước");
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,statusList);
        sp_status.setAdapter(arrayAdapter);
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    mListUser = dao.getListWithStatusOfUser(0);
                    mListUser.addAll(dao.getListWithStatusOfUser(2));
                }
                else if(position == 1)
                    mListUser = dao.getListWithStatusOfUser(0);
                else
                    mListUser = dao.getListWithStatusOfUser(2);
                userAdapter.setData(mListUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                people.setFullName(strUsername);
                people.setSex(Integer.parseInt(strSex));
                people.setSDT(strSDT);
                people.setCCCD(strCCCD);
                people.setAddress(strAddress);
                dao.UpdateUser(people);
                Toast.makeText(getContext(), "Update Thành Công", Toast.LENGTH_SHORT).show();
                loatData();
            }
        });
        builder.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loatData() {
        dao = KhachSanDB.getInstance(getContext()).getDAO();
        mListUser = dao.getListWithStatusOfUser(0);
        mListUser.addAll(dao.getListWithStatusOfUser(2));
        userAdapter.setData(mListUser);
    }
}