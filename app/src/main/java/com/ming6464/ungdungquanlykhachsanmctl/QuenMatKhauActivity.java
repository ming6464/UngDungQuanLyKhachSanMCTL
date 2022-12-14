package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

public class QuenMatKhauActivity extends AppCompatActivity {
    private ProgressBar pg_load;
    private ImageView img_close;
    private Button btn_guiYeuCau;
    private EditText ed_sdt;
    private KhachSanDAO dao;
    private boolean check = true;
    private String pass = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        pg_load = findViewById(R.id.actiQuenMatKhau_pg_load);
        img_close = findViewById(R.id.actiQuenMatKhau_img_close);
        btn_guiYeuCau = findViewById(R.id.actiQuenMatKhau_btn_guiYeuCau);
        ed_sdt = findViewById(R.id.actiQuenMatKhau_ed_sdt);
        dao = KhachSanDB.getInstance(this).getDAO();
    }

    public void handleActionBtnGuiYeuCau(View view) {
        check = true;
        pass = null;
        String sdt = ed_sdt.getText().toString();
        if(sdt.isEmpty()){
            CustomToast.makeText(this,"Thông tin không được bỏ trống !",false);
            return;
        }
        if(!sdt.matches("^0\\d{9}")){
            CustomToast.makeText(QuenMatKhauActivity.this, "Số điện thoại không chính xác !", false).show();
            return;
        }
        //showload
        ed_sdt.setFocusableInTouchMode(false);
        img_close.setEnabled(false);
        btn_guiYeuCau.setEnabled(false);
        pg_load.setVisibility(View.VISIBLE);
        //
        People obj = dao.getObjOfUser(sdt);
        if(obj != null){
            if(obj.getStatus() != 5)
                pass = obj.getPassowrd();
            else
                check = false;
        }



        //hide loađ
        new Handler().postDelayed(() -> {
            ed_sdt.setFocusableInTouchMode(true);
            img_close.setEnabled(true);
            btn_guiYeuCau.setEnabled(true);
            pg_load.setVisibility(View.GONE);
            if(pass != null){
                CustomToast.makeText(QuenMatKhauActivity.this,"Mật khẩu của bạn là: " + pass,true).show();
                finish();
            }else{
                if(check)
                    CustomToast.makeText(QuenMatKhauActivity.this,"Số điện thoại không tồn tại !",false).show();
                else
                    CustomToast.makeText(QuenMatKhauActivity.this,"Số điện thoại bị cấm !",false).show();
            }

        },1000);
        //

    }

    public void handleActionBackToDangNhap(View view) {
        finish();
    }

}