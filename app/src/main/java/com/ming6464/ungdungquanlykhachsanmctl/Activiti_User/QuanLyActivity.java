package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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

public class QuanLyActivity extends AppCompatActivity implements ItemNhanVienAdapter.EventOfItemNhanVienAdapter {
    private RecyclerView recyclerView;
    private KhachSanDAO dao;
    private List<People> list;
    private People people;
    private ItemNhanVienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        //
        Toolbar toolbar = findViewById(R.id.actiQuanLy_tb);
        toolbar.setTitle("Quản Lý Nhân Viên");
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //
        dao = KhachSanDB.getInstance(QuanLyActivity.this).getDAO();
        recyclerView = findViewById(R.id.actiQuanLy_rc_nhanVien);
        handleRecycler();
        findViewById(R.id.actiQuanLy_btn_addNhanVien).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyActivity.this);
            View view = LayoutInflater.from(QuanLyActivity.this).inflate(R.layout.dialog_them_nhanvien, null);
            EditText ed_name = view.findViewById(R.id.dialogThemNhanVien_ed_name);
            EditText ed_sdt = view.findViewById(R.id.dialogThemNhanVien_ed_sdt);
            EditText ed_cccd = view.findViewById(R.id.dialogThemNhanVien_ed_cccd);
            EditText ed_pass = view.findViewById(R.id.dialogThemNhanVien_ed_pass);
            EditText ed_address = view.findViewById(R.id.dialogThemNhanVien_ed_address);
            RadioButton rdo_feMale = view.findViewById(R.id.dialogThemNhanVien_rdo_feMale);
            Button btn_add = view.findViewById(R.id.dialogThemNhanVien_btn_add);
            Button btn_cancel = view.findViewById(R.id.dialogThemNhanVien_btn_cancel);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            btn_add.setOnClickListener(v1 -> {
                //
                String name = ed_name.getText().toString(),sdt = ed_sdt.getText().toString(),
                        cccd = ed_cccd.getText().toString(),pass = ed_pass.getText().toString(),address = ed_address.getText().toString();
                if (name.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || pass.isEmpty() || address.isEmpty()) {
                    CustomToast.makeText(QuanLyActivity.this, "Thông tin khách hàng không để trống", false);
                    return;
                }
                if(dao.checkLogin(sdt) != null){
                    CustomToast.makeText(QuanLyActivity.this,"Nhân Viên Đã Tồn Tại !",false).show();
                    return;
                }
                if(!name.matches("^[a-zA-Z][a-zA-Z ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$")){
                    CustomToast.makeText(QuanLyActivity.this, "Tên không phù hợp", false).show();
                    return;
                }
                if (!sdt.matches("^0\\d{9}")) {
                    CustomToast.makeText(QuanLyActivity.this, "Số điện thoại không đúng !", false).show();
                    return;
                }
                if (cccd.length() < 12) {
                    CustomToast.makeText(QuanLyActivity.this, "CCCD/CMND Không chính xác !", false).show();
                    return;
                }
                if(dao.getObjOfUser(sdt) != null){
                    CustomToast.makeText(QuanLyActivity.this, "Số điện thoại đã tồn tại !", false).show();
                    return;
                }
                if (dao.getObjWithCCCDOfUser(cccd) != null) {
                    CustomToast.makeText(QuanLyActivity.this, "CCCD/CMND đã tồn tại !", false).show();
                    return;
                }
                int sex = 1;
                if (rdo_feMale.isChecked())
                    sex = 0;
                People people = new People(name,sdt,cccd,address,sex,1);
                people.setPassowrd(pass);
                dao.insertOfUser(people);
                list.add(people);
                CustomToast.makeText(QuanLyActivity.this, "Thêm Thành Công", true).show();
                int index = list.size() - 1;
                if(list.size() == 0)
                    index = 0;
                adapter.notifyItemInserted(index);
                dialog.dismiss();
            });
            btn_cancel.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getAttributes().windowAnimations = R.style.dialog_slide_left_to_right;
            dialog.show();
        });
    }

    private void handleRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(QuanLyActivity.this));
        list = dao.getListWithStatusOfUser(1);
        list.addAll(dao.getListWithStatusOfUser(4));
        list.addAll(dao.getListWithStatusOfUser(5));
        adapter = new ItemNhanVienAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(list);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdate(int position) {
        people = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_them_nhanvien, null);
        EditText ed_name = view.findViewById(R.id.dialogThemNhanVien_ed_name);
        EditText ed_sdt = view.findViewById(R.id.dialogThemNhanVien_ed_sdt);
        EditText ed_cccd = view.findViewById(R.id.dialogThemNhanVien_ed_cccd);
        EditText ed_pass = view.findViewById(R.id.dialogThemNhanVien_ed_pass);
        EditText ed_address = view.findViewById(R.id.dialogThemNhanVien_ed_address);
        RadioButton rdo_feMale = view.findViewById(R.id.dialogThemNhanVien_rdo_feMale);
        Button btn_update = view.findViewById(R.id.dialogThemNhanVien_btn_add);
        Button btn_cancel = view.findViewById(R.id.dialogThemNhanVien_btn_cancel);
        builder.setView(view);
        Dialog dialog = builder.create();
        //fill table
        ed_name.setText(people.getFullName());
        ed_sdt.setText(people.getSDT());
        ed_cccd.setText(people.getCCCD());
        ed_pass.setText(people.getPassowrd());
        ed_address.setText(people.getAddress());
        TextView tv_title = view.findViewById(R.id.dialogThemNhanVien_tv_title);
        tv_title.setText("Cập nhật Nhân Viên");
        btn_update.setText("Cập nhật");
        if (people.getSex() == 0)
            rdo_feMale.setChecked(true);
        btn_update.setOnClickListener(v1 -> {
            people.setFullName(ed_name.getText().toString());
            people.setSDT(ed_sdt.getText().toString());
            people.setCCCD(ed_cccd.getText().toString());
            people.setAddress(ed_address.getText().toString());
            people.setPassowrd(ed_pass.getText().toString());
            int sex = 1;
            if (rdo_feMale.isChecked())
                sex = 0;

            people.setSex(sex);
            //
            dao.UpdateUser(people);
            list.set(position, people);
            adapter.notifyItemChanged(position);
            CustomToast.makeText(this, "Cập Nhật Thành Công", true).show();
            dialog.cancel();
        });
        btn_cancel.setOnClickListener(v1 -> {
            dialog.cancel();
        });
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations = R.style.dialog_slide_left_to_right;
        dialog.show();
    }

    @Override
    public void onDelete(int position) {
        people = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh Báo");
        builder.setMessage("Xóa sẽ làm mất dữ liệu bạn vẫn muốn xóa");
        builder.setNegativeButton("Xoá", (dialog, which) -> {
            dao.DeleteUser(people);
            list.remove(position);
            adapter.notifyItemRemoved(position);
            CustomToast.makeText(QuanLyActivity.this, "Xóa Thành Công", true).show();
        });
        builder.setPositiveButton("Huỷ", (dialog, which) -> {
        });
        builder.show();
    }
}