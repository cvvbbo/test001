package com.t.test001;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BasePermissionsActivity extends AppCompatActivity {


    private static final int permissionsRequestCode = 2;

    String dialogtitle = setPermissionTitle() + "权限权限不可用";
    String contenttitle = "为了APP的正常使用,需要开启" + setPermissionTitle() + "权限\n否则，部分功能将无法正常使用";


    public void initPermission(String[] permissions) {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {//for循环把需要授权的权限都添加进来
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {  //未授权就进行授权
                    permissionList.add(permissions[i]);
                }
            }
            //如果permissionList是空的，说明没有权限需要授权,什么都不做，该干嘛干嘛，否则就发起授权请求
            if (!permissionList.isEmpty()) {
                showDialogTipUserRequestPermission(permissionList, dialogtitle, contenttitle);
                // 新申请的权限重置。
                CheckPermissionUtils.open_one = true;
            } else {
                // todo 如果权限都有了，则执行有权限的方法
                doAfterPermission();
            }
        } else {
            doAfterPermission();
        }
    }

    private void showDialogTipUserRequestPermission(final List<String> permissionList, String title, String content) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission(permissionList);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission(permissionList);
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission(List<String> permissionList) {
        if (!permissionList.isEmpty()) {//不为空就进行授权申请
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, permissionsRequestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionsRequestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults.length > 0) {//安全写法，如果小于0，肯定会出错了
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        switch (grantResult) {
                            case PackageManager.PERMISSION_GRANTED://同意授权0
                                // doAfterPermission();
                                CheckPermissionUtils.getPermissionResult(this, permissions, () -> {
                                    doAfterPermission();
                                });
                                break;
                            case PackageManager.PERMISSION_DENIED://拒绝授权-1
//                                Utils.ShowToast(context,permissions[i]+"权限获取失败");
                                doAfterPermission();
                                Toast.makeText(this, "权限获取失败\"请去\"设置\"中开启本应用的相机和图片媒体访问权限", Toast.LENGTH_SHORT).show();
                                //getActivity().finish();
                                Log.e("BasePermissionsActivity", permissions[i]);
                                break;
                        }
                    }
                }
            }
        }
    }


    /**
     * 得到权限的中文名字
     * <p>
     * <p>
     * todo
     * 提示用户哪些权限不能够使用
     * 当大于一一个权限的时候，则提示   相关权限不可用
     * 只有当只有一个权限的时候，则提示 具体的权限名字
     */
    public String setPermissionTitle() {
        return null;
    }


    /**
     * 授权之后的行为
     */
    public void doAfterPermission() {

    }


}
