package com.t.test001;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;


/**
 * Author: xiong
 * Time: 2020/8/30 0:00
 */
public class CheckPermissionUtils {


    //  一次多组权限的时候，onRequestPermissionsResult 也会走多次
    //  如果封装成工具类这两个成员变量也要重置。
    public static int group_num;
    //  因为一次申请多个权限，所以回调也会走多次，遇到下次有新的权限的时候需要重置这个变量
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
                onAgreePermission.doAgreePermission();
                open_one = false;
            }
        }
    }


    interface OnAgreePermission {
        void doAgreePermission();
    }
}
