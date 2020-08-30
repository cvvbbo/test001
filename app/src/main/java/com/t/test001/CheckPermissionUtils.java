package com.t.test001;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

/**
 * Author: xiong
 * Time: 2020/8/30 0:00
 */
public class CheckPermissionUtils {


    //  一次多组权限的时候，onRequestPermissionsResult 也会走多次
    //  如果封装成工具类这两个成员变量也要重置。
    public static int group_num;
    public static boolean open_one = true;


    public static void getPermissionResult(Context context,
                                           String[] permissions,
                                           OnAgreePermission onAgreePermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && open_one) {
            for (String permissions_name : permissions) {
                if (context.checkSelfPermission(permissions_name) == PackageManager.PERMISSION_GRANTED) {
                    group_num++;
                }
            }
            if (group_num == permissions.length && open_one) {
                // todo do something
                onAgreePermission.AgreePermission();
                open_one = false;
            }
        }
    }


    interface OnAgreePermission {
        void AgreePermission();
    }
}
