package com.ming6464.ungdungquanlykhachsanmctl.Activiti_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanSharedPreferences;
import com.ming6464.ungdungquanlykhachsanmctl.R;

public class UpdateThongTinActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText ed_name, ed_sdt, ed_address;
    private TextView tv_tb;
    private KhachSanSharedPreferences share;
    private RadioButton rdo_feMale;
    private People people;
    private String fullName;
    private ImageView img_avatar;
    private KhachSanDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_thong_tin);
        dao = KhachSanDB.getInstance(this).getDAO();
        share = new KhachSanSharedPreferences(this);
        toolbar = findViewById(R.id.actiUpdateThongTin_tb);
        anhXa();
        upData();
    }

    private void upData() {
        tv_tb.setText("Cập Nhật Thông Tin");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        people = dao.getUserBy(share.getSDT2());
        fullName = people.getFullName();
        int index = fullName.lastIndexOf(" ") + 1;
        if(index > 1)
            fullName = fullName.substring(index);
        ed_name.setText(people.getFullName());
        ed_address.setText(people.getAddress());
        ed_sdt.setText(people.getSDT());
        if (people.getSex() == 0){
            img_avatar.setImageResource(R.drawable.businesswoman_100);
            rdo_feMale.setChecked(true);
        }

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
        ed_name = findViewById(R.id.actiUpdateThongTin_ed_name);
        ed_sdt = findViewById(R.id.actiUpdateThongTin_ed_sdt);
        ed_address = findViewById(R.id.actiUpdateThongTin_ed_address);
        rdo_feMale = findViewById(R.id.actiUpdateThongTin_rdo_feMale);
        tv_tb = findViewById(R.id.actiUpdateThongTin_tv_tb);
        img_avatar = findViewById(R.id.actiUpdateThongTin_img_avatar);
    }

    public void handleActionBtnSave(View view) {
        fullName = ed_name.getText().toString();
        String phoneNumber = ed_sdt.getText().toString();
        String addrress = ed_address.getText().toString();
        if(fullName.isEmpty() || phoneNumber.isEmpty() || addrress.isEmpty()){
            CustomToast.makeText(UpdateThongTinActivity.this, "Thông tin trống !", false).show();
            return;
        }
        if(!fullName.matches("^[a-zA-Z][a-zA-Z ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$")){
            CustomToast.makeText(UpdateThongTinActivity.this, "Tên không phù hợp", false).show();
            return;
        }
        if(!phoneNumber.matches("^0\\d{9}$")){
            CustomToast.makeText(UpdateThongTinActivity.this, "Số điện thoại không đúng !", false).show();
            return;
        }
        int sex = 1;
        if(rdo_feMale.isChecked())
            sex = 0;
        people.setFullName(fullName);
        people.setAddress(addrress);
        people.setSDT(phoneNumber);
        people.setSex(sex);
        dao.UpdateUser(people);
        CustomToast.makeText(UpdateThongTinActivity.this,"Cập nhật thành công !", true).show();
        this.finish();
    }
}