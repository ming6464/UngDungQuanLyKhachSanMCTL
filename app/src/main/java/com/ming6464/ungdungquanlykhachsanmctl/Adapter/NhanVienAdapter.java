package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {
    private List<People> listNv;
    private Context context;
    KhachSanDB db;

    public NhanVienAdapter(List<People> listNv, Context context) {
        this.listNv = listNv;
        this.context = context;
        this.db = KhachSanDB.getInstance(context);
    }

    @Override
    public NhanVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienAdapter.ViewHolder holder, int position) {
        People people = listNv.get(holder.getAdapterPosition());
        int index = position;
        holder.tvNameNv.setText("Họ và tên : " + people.getFullName());
        holder.tvCccdNv.setText("CCCD : " + people.getCCCD());
        holder.tvSdtNv.setText("Số Đt : " + people.getSDT());
        if (people.getSex() == 1) {
            holder.tvGioiTinhNv.setText("Giới Tính :  Nam");
        } else {
            holder.tvGioiTinhNv.setText("Giới Tính :  Nữ");
            holder.imgAvatar.setImageResource(R.drawable.businesswoman_100);
        }
        holder.tvPassWordNv.setText("Mật Khẩu :  " + people.getPassowrd());
        holder.tvDiaChiNv.setText("Địa Chỉ :  " + people.getAddress());
        if (people.getStatus() == 2) {
            holder.tvNameNv.setTextColor(Color.BLACK);
        } else if (people.getStatus() == 5) {
            holder.tvNameNv.setTextColor(Color.RED);
            holder.itemView.setEnabled(false);
            holder.imgAvatar.setBackgroundResource(R.drawable.back_nghi_viec);
        }else if(people.getStatus() == 4){
            holder.imgAvatar.setBackgroundResource(R.drawable.back_nghi_phep);
        }

        holder.imgXoaNv.setOnClickListener(v -> {
            //get status
            //tìm theo sdt
            //a
            ///cách 2
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Vui lòng chọn trạng thái của nhân viên");
            String th1 = "Nghỉ Phép", th2 = "Hủy";
            int status = people.getStatus();
            if (status == 2) {
                th2 = "Nghỉ Việc";
            } else if (status == 4) {
                th2 = "Nghỉ Việc";
                th1 = "Hoạt động trợ lại";
            } else if (status == 5) {
                th1 = "Hoạt động trở lại";
                th2 = "Hủy";
            }


            builder.setNegativeButton(th1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    holder.itemView.setEnabled(false);
                    CustomToast.makeText(context, "Đã Nghỉ", true);
                    people.setStatus(5);
                    db.getDAO().UpdateUser(people);
                    notifyItemChanged(index);
                    listNv.set(index, people);

                }
            });
            builder.setPositiveButton(th2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    holder.tvNameNv.setTextColor(Color.BLUE);
                    holder.itemView.setEnabled(true);
                    people.setStatus(4);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        });
        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_nhanvien, null);
            EditText edName = view.findViewById(R.id.edNameNv);
            EditText edSdt = view.findViewById(R.id.edSoDtNv);
            EditText edCccd = view.findViewById(R.id.edCCCDNv);
            EditText edPass = view.findViewById(R.id.edPassNv);
            EditText edAddress = view.findViewById(R.id.edAddressNv);
            RadioButton rdoNam = view.findViewById(R.id.rdo_nam);
            RadioButton rdoNu = view.findViewById(R.id.rdo_nu);
            Button btnSave = view.findViewById(R.id.btnLuuNv);
            Button btnCancleNv = view.findViewById(R.id.btnCancleNv);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            //fill table
            edName.setText(people.getFullName());
            edSdt.setText(people.getSDT());
            edCccd.setText(people.getCCCD());
            edPass.setText(people.getPassowrd());
            edAddress.setText(people.getAddress());
            TextView tv = view.findViewById(R.id.tvHi1);
            tv.setText("Cập nhật Nhân Viên");
            btnSave.setText("Cập nhật");
            if (people.getSex() == 1) {
                rdoNam.setChecked(true);
            } else {
                rdoNu.setChecked(true);
            }

            btnSave.setOnClickListener(v1 -> {
                people.setFullName(edName.getText().toString());
                people.setSDT(edSdt.getText().toString());
                people.setCCCD(edCccd.getText().toString());
                people.setAddress(edAddress.getText().toString());
                people.setPassowrd(edPass.getText().toString());
                int sex = 0;
                if (rdoNam.isChecked()) {
                    sex = 1;
                }
                people.setSex(sex);
                //
                try {
                    db.getDAO().UpdateUser(people);
                    listNv.set(index, people);
                    this.notifyItemChanged(index);
                    dialog.dismiss();
                    CustomToast.makeText(context, "Cập Nhật Thành Công", true).show();
                } catch (Exception e) {
                    CustomToast.makeText(context, "Cập Nhật Thất Bại", false);
                }
            });
            btnCancleNv.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        if (listNv == null)
            return 0;
        return listNv.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameNv, tvSdtNv, tvCccdNv, tvGioiTinhNv, tvPassWordNv, tvDiaChiNv;
        ImageView imgXoaNv, imgAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameNv = itemView.findViewById(R.id.tvNameNv);
            tvSdtNv = itemView.findViewById(R.id.tvSdtNv);
            tvCccdNv = itemView.findViewById(R.id.tvCccdNv);
            tvGioiTinhNv = itemView.findViewById(R.id.tvGioiTinhNv);
            tvPassWordNv = itemView.findViewById(R.id.tvPassWordNv);
            tvDiaChiNv = itemView.findViewById(R.id.tvDiaChiNv);
            imgXoaNv = itemView.findViewById(R.id.btnXoaNv);
            imgAvatar = itemView.findViewById(R.id.itemNhanVien_img_avatar);
        }
    }
}
