package ir.sarvwood.workshop.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PublicFunctions {



    public String  getAppVersionCode(Context context) {
        String versionCode = "0";
        try {
            PackageInfo pInfo;
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
