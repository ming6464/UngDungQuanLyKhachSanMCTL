package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

public class UpdateUserActivity extends AppCompatActivity {
    private EditText edtUsername, edtAddress, edtSex, edtSDT, edtCCCD;
    private Button btnUpdateUser;
    private People mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        edtUsername = findViewById(R.id.edt_username);
        edtSex = findViewById(R.id.edt_sex);
        edtSDT = findViewById(R.id.edt_sdt);
        edtCCCD = findViewById(R.id.edt_cccd);
        edtAddress = findViewById(R.id.edt_address);
        btnUpdateUser = findViewById(R.id.btn_update_user);
        mUser = (People) getIntent().getExtras().get("obj_user");
        if (mUser != null) {
            edtUsername.setText(mUser.getFullName());
            edtSex.setText(String.valueOf(mUser.getSex()));
            edtSDT.setText(mUser.getSDT());
            edtCCCD.setText(mUser.getCCCD());
            edtAddress.setText(mUser.getAddress());
        }
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }
    private void updateUser() {
        String strUsername = edtUsername.getText().toString().trim();
        String strSex = edtSex.getText().toString().trim();
        String strSDT = edtSDT.getText().toString().trim();
        String strCCCD = edtCCCD.getText().toString().trim();
        String strAddress = edtAddress.getText().toString().trim();
        if (TextUtils.isEmpty(strUsername)
                || TextUtils.isEmpty(strSex)
                || TextUtils.isEmpty(strSDT)
                || TextUtils.isEmpty(strCCCD)
                || TextUtils.isEmpty(strAddress)) {
            return;
        }
        mUser.setFullName(strUsername);
        mUser.setSex(Integer.parseInt(strSex));
        mUser.setSDT(strSDT);
        mUser.setCCCD(strCCCD);
        mUser.setAddress(strAddress);
        KhachSanDB.getInstance(this).getDAO().UpdateUser(mUser);
        Toast.makeText(this, "Update thanh cong", Toast.LENGTH_SHORT).show();
        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        finish();
    }
}