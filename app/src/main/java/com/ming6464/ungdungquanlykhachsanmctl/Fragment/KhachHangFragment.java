package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.UserAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
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
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        userAdapter = new UserAdapter(requireContext(), new UserAdapter.IClickItemUser() {
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
        statusList.add("Mới");
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,statusList);
        sp_status.setAdapter(arrayAdapter);
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mListUser = new ArrayList<>();
                        for(People x : dao.getListUser()){
                            if(x.getStatus() == 0 || x.getStatus() == 2)
                                mListUser.add(x);
                        }
                        break;
                    case 1:
                        mListUser = dao.getListWithStatusOfUser(0);
                        break;
                    default:
                        mListUser = dao.getListWithStatusOfUser(2);
                }
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
                        CustomToast.makeText(getContext(), "Xoa thanh cong", true).show();
                        loatData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickUpdateUser(People people) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_nhanvien, null);
        EditText edtUsername = view.findViewById(R.id.edNameNv);
        EditText edtSDT = view.findViewById(R.id.edSoDtNv);
        EditText edtCCCD = view.findViewById(R.id.edCCCDNv);
        EditText edtAddress = view.findViewById(R.id.edAddressNv);
        view.findViewById(R.id.edPassNv).setVisibility(View.GONE);
        Button btnUp = view.findViewById(R.id.btnLuuNv);
        Button btnCancle = view.findViewById(R.id.btnCancleNv);
        RadioButton rdoNam = view.findViewById(R.id.rdo_nam);
        RadioButton rdoNu = view.findViewById(R.id.rdo_nu);
        TextView tv = view.findViewById(R.id.tvHi1);
        tv.setText("Cập nhật Khách Hàng");
        btnUp.setText("Cập nhật");

        //set data
        edtUsername.setText(people.getFullName());
        edtSDT.setText(people.getSDT());
        edtCCCD.setText(people.getCCCD());
        edtAddress.setText(people.getAddress());
        if (people.getSex() == 1) {
            rdoNam.setChecked(true);
        } else {
            rdoNu.setChecked(true);
        }
        builder.setView(view);
        //
        AlertDialog dialog = builder.create();

        btnUp.setOnClickListener(v -> {
            String strUsername = edtUsername.getText().toString().trim();
            String strSDT = edtSDT.getText().toString().trim();
            String strCCCD = edtCCCD.getText().toString().trim();
            String strAddress = edtAddress.getText().toString().trim();
            int sex = 0;
            if (rdoNam.isChecked()) {
                sex = 1;
            }//

            people.setFullName(strUsername);
            people.setSDT(strSDT);
            people.setCCCD(strCCCD);
            people.setAddress(strAddress);
            people.setSex(sex);
            dao.UpdateUser(people);
            CustomToast.makeText(getContext(), "Cập nhật Thành Công", true).show();
            loatData();
            dialog.dismiss();

        });
        btnCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //cc
    }

    private void loatData() {
        mListUser = new ArrayList<>();
        for(People x : dao.getListUser()){
            if(x.getStatus() == 0 || x.getStatus() == 2)
                mListUser.add(x);
        }
        userAdapter.setData(mListUser);
    }
}