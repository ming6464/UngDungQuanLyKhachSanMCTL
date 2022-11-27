package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ming6464.ungdungquanlykhachsanmctl.Adapter.NhanVienAdapter;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_QuanLy extends AppCompatActivity {
    FloatingActionButton btnThem;
    RecyclerView recyclerView;
    Toolbar toolbar;
    KhachSanDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        //
        toolbar = findViewById(R.id.toolbar_thong_tin);
        toolbar.setTitle("Quản Lý Nhân Viên");
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //
        dao = KhachSanDB.getInstance(Activity_QuanLy.this).getDAO();
        List<People> listNv = new ArrayList<>();
        btnThem = findViewById(R.id.btnAddNhanVien);
        recyclerView = findViewById(R.id.recyclerViewNhanVien);
        show();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_QuanLy.this);
                View view = LayoutInflater.from(Activity_QuanLy.this).inflate(R.layout.dialog_add_nhanvien, null);
                EditText edName = view.findViewById(R.id.edNameNv);
                EditText edSdt = view.findViewById(R.id.edSoDtNv);
                EditText edCccd = view.findViewById(R.id.edCCCDNv);
                EditText edPass = view.findViewById(R.id.edPassNv);
                EditText edAddress = view.findViewById(R.id.edAddressNv);
                RadioButton rdoNam = view.findViewById(R.id.rdo_nam);
                Button btnSave = view.findViewById(R.id.btnLuuNv);
                Button btnCancleNv = view.findViewById(R.id.btnCancleNv);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                btnSave.setOnClickListener(v1 -> {
                    //
                    String name = edName.getText().toString(),sdt = edSdt.getText().toString(),
                            cccd = edCccd.getText().toString(),pass = edPass.getText().toString(),address = edAddress.getText().toString();
                    if (name.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || pass.isEmpty() || address.isEmpty()) {
                        CustomToast.makeText(Activity_QuanLy.this, "Thông tin khách hàng không để trống", false);
                        return;
                    }
                    if(dao.checkLogin(sdt) != null){
                        CustomToast.makeText(Activity_QuanLy.this,"Nhân Viên Đã Tồn Tại !",false).show();
                        return;
                    }
                    if(!name.matches("^[a-zA-Z][a-zA-Z ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$")){
                        CustomToast.makeText(Activity_QuanLy.this, "Tên không phù hợp", false).show();
                        return;
                    }
                    if (!sdt.matches("^0\\d{9}")) {
                        CustomToast.makeText(Activity_QuanLy.this, "Số điện thoại không đúng !", false).show();
                        return;
                    }
                    if (cccd.length() < 12) {
                        CustomToast.makeText(Activity_QuanLy.this, "CCCD/CMND Không chính xác !", false).show();
                        return;
                    }
                    int sex = 0;
                    if (rdoNam.isChecked()) {
                        sex = 1;
                    }
                    People people = new People(name,sdt,cccd,address,sex,3);
                    people.setPassowrd(pass);
                    dao.insertOfUser(people);
                    listNv.add(people);
                    CustomToast.makeText(Activity_QuanLy.this, "Thêm Thành Công", true).show();
                    dialog.dismiss();
                    show();
                });
                btnCancleNv.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void show() {
        LinearLayoutManager manager = new LinearLayoutManager(Activity_QuanLy.this);
        recyclerView.setLayoutManager(manager);
        List<People> list = dao.getListWithStatusOfUser(3);
        NhanVienAdapter adapter = new NhanVienAdapter(list, Activity_QuanLy.this);
        recyclerView.setAdapter(adapter);
    }
}