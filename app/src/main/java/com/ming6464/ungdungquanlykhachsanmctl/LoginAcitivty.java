package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.Activiti_User.Activity_ThongTin;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

public class LoginAcitivty extends AppCompatActivity {
    Button btnLogin;
    EditText edUser, edPass;
    KhachSanDB db;
    //
    CheckBox chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivty);
        btnLogin = findViewById(R.id.btnLogin);
        edUser = findViewById(R.id.edUser);
        edPass = findViewById(R.id.edPass);
        chk = findViewById(R.id.chkCheck);
        //save data
        SharedPreferences pePreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edUser.setText(pePreferences.getString("USERNAME", ""));
        edPass.setText(pePreferences.getString("PASSWORD", ""));
        chk.setChecked(pePreferences.getBoolean("REMEMBER", false));
        //
        db = KhachSanDB.getInstance(LoginAcitivty.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = edUser.getText().toString();
                String p = edPass.getText().toString();

                if (u.isEmpty() || p.isEmpty()) {
                    Toast.makeText(LoginAcitivty.this, "Vui Lòng Không Để Trống Thông Tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                People people = db.getDAO().checkLogin(u, p);
                if (people != null) {
                    //rember
                    rememberUser(u, p, chk.isChecked());
                    Intent intent = new Intent(LoginAcitivty.this, MainActivity.class);
                    intent.putExtra("user", u);
                    startActivity(intent);
                    Toast.makeText(LoginAcitivty.this, "Login Thành Công", Toast.LENGTH_SHORT).show();
                    //

                } else {
                    Toast.makeText(LoginAcitivty.this, "Login Thất Bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void rememberUser(String us, String pass, boolean check) {
        SharedPreferences pePreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pePreferences.edit();
        if (check) {
            editor.putString("USERNAME", us);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", true);
        } else {
            editor.clear();
        }
        editor.commit();
    }
}