package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class ItemNhanVienAdapter extends RecyclerView.Adapter<ItemNhanVienAdapter.ViewHolder> {
    private List<People> listNv;
    private Context context;
    KhachSanDB db;

    public ItemNhanVienAdapter(List<People> listNv, Context context) {
        this.listNv = listNv;
        this.context = context;
        this.db = KhachSanDB.getInstance(context);
    }

    @Override
    public ItemNhanVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNhanVienAdapter.ViewHolder holder, int position) {
        People people = listNv.get(holder.getAdapterPosition());
        int index = position;
        holder.tv_name.setText("Họ và tên : " + people.getFullName());
        holder.tv_cccd.setText("CCCD : " + people.getCCCD());
        holder.tv_sdt.setText("Số Đt : " + people.getSDT());
        if (people.getSex() == 1) {
            holder.tv_sex.setText("Giới Tính :  Nam");
        } else {
            holder.tv_sex.setText("Giới Tính :  Nữ");
            holder.img_avatar.setImageResource(R.drawable.businesswoman_100);
        }
        holder.tv_pass.setText("Mật Khẩu :  " + people.getPassowrd());
        holder.tv_address.setText("Địa Chỉ :  " + people.getAddress());
        holder.img_xoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh Báo");
            builder.setMessage("Xóa sẽ làm mất dữ liệu bạn vẫn muốn xóa");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.getDAO().DeleteUser(people);
                    listNv.remove(index);
                    notifyItemRemoved(index);
                    CustomToast.makeText(context, "Xóa Thành Công", true).show();
                }
            });
            builder.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_them_nhanvien, null);
            EditText ed_name = view.findViewById(R.id.dialogThemNhanVien_ed_name);
            EditText ed_sdt = view.findViewById(R.id.dialogThemNhanVien_ed_sdt);
            EditText ed_cccd = view.findViewById(R.id.dialogThemNhanVien_ed_cccd);
            EditText ed_pass = view.findViewById(R.id.dialogThemNhanVien_ed_pass);
            EditText ed_address = view.findViewById(R.id.dialogThemNhanVien_ed_address);
            RadioButton rdo_feMale = view.findViewById(R.id.dialogThemNhanVien_rdo_feMale);
            Button btn_update = view.findViewById(R.id.dialogThemNhanVien_btn_add);
            Button btn_cancel = view.findViewById(R.id.dialogThemNhanVien_btn_cancel);
            builder.setView(view);
            AlertDialog dialog = builder.create();
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
            btn_cancel.setOnClickListener(v1 -> {
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
        TextView tv_name, tv_sdt, tv_cccd, tv_sex, tv_pass, tv_address;
        ImageView img_xoa, img_avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemNhanVien_tv_name);
            tv_sdt = itemView.findViewById(R.id.itemNhanVien_tv_sdt);
            tv_cccd = itemView.findViewById(R.id.itemNhanVien_tv_cccd);
            tv_sex = itemView.findViewById(R.id.itemNhanVien_tv_sex);
            tv_pass = itemView.findViewById(R.id.itemNhanVien_tv_pass);
            tv_address = itemView.findViewById(R.id.itemNhanVien_tv_address);
            img_xoa = itemView.findViewById(R.id.itemNhanVien_img_xoa);
            img_avatar = itemView.findViewById(R.id.itemNhanVien_img_avatar);
        }
    }
}
