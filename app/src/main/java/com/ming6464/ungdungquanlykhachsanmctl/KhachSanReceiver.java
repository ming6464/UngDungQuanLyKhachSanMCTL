package com.ming6464.ungdungquanlykhachsanmctl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class KhachSanReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,KhachSanService.class);
        intent1.putExtra(ChucNangDatPhongActivity.KEY_ALARM, intent.getBooleanExtra(ChucNangDatPhongActivity.KEY_ALARM,false));
        context.startService(intent1);
    }
}
