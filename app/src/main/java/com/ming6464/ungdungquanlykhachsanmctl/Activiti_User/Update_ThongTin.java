package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanSharedPreferences;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import org.w3c.dom.Text;

public class Update_ThongTin extends AppCompatActivity {
    Toolbar toolbar;
    EditText edName, edSdt, edDiaChi;
    TextView title,tvTbName;
    KhachSanSharedPreferences share;
    RadioButton rdoN;
    private People people;
    private String fullName;
    KhachSanDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_thong_tin);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        toolbar = findViewById(R.id.toolbar_thong_tin);
        anhXa();
        upData();
    }

    private void upData() {
        tvTbName.setText("Cập Nhật Thông Tin");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        people = dao.getUserBy(share.getSDT2());
        fullName = people.getFullName();
        int index = fullName.lastIndexOf(" ");
        if(index > 0)
            fullName = fullName.substring(index);
        title.setText("Welcome to" + fullName);
        edName.setText(people.getFullName());
        edDiaChi.setText(people.getAddress());
        edSdt.setText(people.getSDT());
        if (people.getSex() == 0)
            rdoN.setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //ánh xa
    private void anhXa() {
        edName = findViewById(R.id.edU);
        edSdt = findViewById(R.id.edN);
        edDiaChi = findViewById(R.id.edA);
        rdoN = findViewById(R.id.rdo2);
        title = findViewById(R.id.tv_update);
        tvTbName = findViewById(R.id.actiUThongTin_tv_tbName);
    }

    public void handleActionBtnSave(View view) {
        fullName = edName.getText().toString();
        String phoneNumber = edSdt.getText().toString();
        String addrress = edDiaChi.getText().toString();
        if(fullName.isEmpty() || phoneNumber.isEmpty() || addrress.isEmpty()){
            CustomToast.makeText(Update_ThongTin.this, "Thông tin trống !", false).show();
            return;
        }
        if(!fullName.matches("^[a-zA-Z][a-zA-Z ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$")){
            CustomToast.makeText(Update_ThongTin.this, "Tên không phù hợp", false).show();
            return;
        }
        if(!phoneNumber.matches("^0\\d{9}$")){
            CustomToast.makeText(Update_ThongTin.this, "Số điện thoại không đúng !", false).show();
            return;
        }
        int sex = 1;
        if(rdoN.isChecked())
            sex = 0;
        people.setFullName(fullName);
        people.setAddress(addrress);
        people.setSDT(phoneNumber);
        people.setSex(sex);
        dao.UpdateUser(people);
        CustomToast.makeText(Update_ThongTin.this,"Cập nhật thành công !", true).show();
        this.finish();
    }
}