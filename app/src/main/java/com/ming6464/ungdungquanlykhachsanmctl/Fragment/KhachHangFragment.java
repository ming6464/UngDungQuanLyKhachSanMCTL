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
import android.widget.SearchView;
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

public class KhachHangFragment extends Fragment implements UserAdapter.IClickItemUser {
    public RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private List<People> mListUser;
    private KhachSanDAO dao;
    private SearchView searchView;

    public static KhachHangFragment newInstance() {
        return new KhachHangFragment();
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
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        //chức năng tìm kiếm
        searchView = view.findViewById(R.id.searchKh);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mListUser.clear();
                mListUser.addAll(dao.getSearchView("%"+newText+"%"));
                userAdapter.notifyDataSetChanged();
                return false;
            }
        });
        mListUser = dao.getListKhachHangOfUser();
        userAdapter = new UserAdapter(this);
        rcvUser.setAdapter(userAdapter);
        rcvUser.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter.setData(mListUser);
    }


    private void timKiem(String sdt) {
        List<People> listSearch = new ArrayList<>();
        for (People people : mListUser) {

        }
    }

    @Override
    public void updateUser(int position) {
        People people = mListUser.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_nhanvien, null);
        EditText edtUsername = view.findViewById(R.id.edNameNv);
        EditText edtSDT = view.findViewById(R.id.edSoDtNv);
        EditText edtCCCD = view.findViewById(R.id.edCCCDNv);
        EditText edtAddress = view.findViewById(R.id.edAddressNv);
        view.findViewById(R.id.dialogAddNhanVien_inputLayout_pass).setVisibility(View.GONE);
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
            userAdapter.notifyItemChanged(position);
            dialog.dismiss();

        });
        btnCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}