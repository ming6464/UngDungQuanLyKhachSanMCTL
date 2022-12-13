package com.ming6464.ungdungquanlykhachsanmctl.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ming6464.ungdungquanlykhachsanmctl.Adapter.ItemNhanVienAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;
import java.util.List;

public class KhachHangFragment extends Fragment implements ItemNhanVienAdapter.EventOfItemNhanVienAdapter {
    public RecyclerView rcvUser;
    private ItemNhanVienAdapter userAdapter;
    private List<People> mListUser;
    private KhachSanDAO dao;

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
        rcvUser = view.findViewById(R.id.fragKhachHang_rc);
        dao = KhachSanDB.getInstance(requireContext()).getDAO();
        //chức năng tìm kiếm
        SearchView searchView = view.findViewById(R.id.fragKhachHang_search);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mListUser = dao.searchKhachHangWithPhoneNumber("%"+newText+"%");
                userAdapter.setData(mListUser);
                return false;
            }
        });
        mListUser = dao.getListKhachHangOfUser();
        userAdapter = new ItemNhanVienAdapter(this);
        rcvUser.setAdapter(userAdapter);
        rcvUser.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter.setData(mListUser);
    }



    @Override
    public void onUpdate(int position) {
        People people = mListUser.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_nhanvien, null);
        EditText ed_Username = view.findViewById(R.id.dialogThemNhanVien_ed_name);
        EditText ed_SDT = view.findViewById(R.id.dialogThemNhanVien_ed_sdt);
        EditText ed_CCCD = view.findViewById(R.id.dialogThemNhanVien_ed_cccd);
        EditText ed_Address = view.findViewById(R.id.dialogThemNhanVien_ed_address);
        view.findViewById(R.id.dialogAddNhanVien_inputLayout_pass).setVisibility(View.GONE);
        Button btn_update = view.findViewById(R.id.dialogThemNhanVien_btn_add);
        Button btn_cancle = view.findViewById(R.id.dialogThemNhanVien_btn_cancel);
        RadioButton rdo_feMale = view.findViewById(R.id.dialogThemNhanVien_rdo_feMale);
        TextView tv = view.findViewById(R.id.dialogThemNhanVien_tv_title);
        tv.setText("Cập nhật Khách Hàng");
        btn_update.setText("Cập nhật");

        //set data
        ed_Username.setText(people.getFullName());
        ed_SDT.setText(people.getSDT());
        ed_CCCD.setText(people.getCCCD());
        ed_Address.setText(people.getAddress());
        if (people.getSex() == 0)
            rdo_feMale.setChecked(true);
        builder.setView(view);
        //
        AlertDialog dialog = builder.create();

        btn_update.setOnClickListener(v -> {
            String name = ed_Username.getText().toString(),
                    sdt = ed_SDT.getText().toString(),
                    cccd = ed_CCCD.getText().toString(),
                    address = ed_Address.getText().toString();
            int sex = 1;
            if (rdo_feMale.isChecked())
                sex = 0;
            if(name.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || address.isEmpty()){
                CustomToast.makeText(requireContext(), "Thông tin không được bỏ trống !", false).show();
                return;
            }
            if(!name.matches("^[a-zA-Z ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$")){
                CustomToast.makeText(requireContext(), "Tên không phù hợp", false).show();
                return;
            }
            if(!sdt.matches("^0\\d{9}$")){
                CustomToast.makeText(requireContext(), "Số điện thoại không đúng !", false).show();
                return;
            }else if(dao.getAmountWithPhoneNumberPeople(sdt) > 1) {
                CustomToast.makeText(requireContext(), "Số điện thoại đã tồn tại !", false).show();
                return;
            }
            if(cccd.length() != 12){
                CustomToast.makeText(requireContext(), "CCCD/CMND Không chính xác !", false).show();
                return;
            }else if(dao.getAmountWithCCCDPeople(cccd) > 1){
                CustomToast.makeText(requireContext(), "CCCD/CMND đã tồn tại !", false).show();
                return;
            }
            people.setFullName(name);
            people.setSDT(sdt);
            people.setCCCD(cccd);
            people.setAddress(address);
            people.setSex(sex);
            dao.UpdateUser(people);
            CustomToast.makeText(getContext(), "Cập nhật Thành Công", true).show();
            userAdapter.notifyItemChanged(position);
            dialog.dismiss();//
        });
        btn_cancle.setOnClickListener(v -> {
            dialog.dismiss();
        });

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations = R.style.dialog_slide_left_to_right;
        dialog.show();
    }

}