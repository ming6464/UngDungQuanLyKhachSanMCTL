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
import android.widget.Toast;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

public class Update_ThongTin extends AppCompatActivity {
    Toolbar toolbar;
    EditText edName, edSdt, edCccc, edDiaChi;
    RadioButton rdoN, rdoNu;
    Button btnUp;
    KhachSanDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_thong_tin);
        toolbar = findViewById(R.id.toolbar_thong_tin);
        anhXa();
        toolbar.setTitle("        Update Thông Tin");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //
        db = KhachSanDB.getInstance(Update_ThongTin.this);
        String u = getIntent().getStringExtra("s");
        Log.d("zzz", "Update : " + u);
        People people = db.getDAO().getUserBy(u);
        edName.setText(people.getFullName());
        edCccc.setText(people.getCCCD());
        edDiaChi.setText(people.getAddress());
        edSdt.setText(people.getSDT());
        if (people.getSex() == 1) {
            rdoN.setChecked(true);
        } else {
            rdoNu.setChecked(true);
        }
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set data
                people.setFullName(edName.getText().toString());
                people.setCCCD(edCccc.getText().toString());
                people.setAddress(edDiaChi.getText().toString());
                people.setSDT(edSdt.getText().toString());
                try {
                    db.getDAO().UpdateUser(people);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_ThongTin.this);
                    builder.setTitle("Cập Nhật Thành Công");
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (Exception e) {
                    Toast.makeText(Update_ThongTin.this, "Cập Nhật Không Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //ánh xa
    private void anhXa() {
        edName = findViewById(R.id.edU);
        edSdt = findViewById(R.id.edN);
        edCccc = findViewById(R.id.edC);
        edDiaChi = findViewById(R.id.edA);
        rdoN = findViewById(R.id.rdo1);
        rdoNu = findViewById(R.id.rdo_nu);
        btnUp = findViewById(R.id.btn1);
    }
}