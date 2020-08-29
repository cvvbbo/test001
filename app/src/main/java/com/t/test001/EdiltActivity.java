package com.t.test001;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.FileProvider;

/**
 * Author: xiong
 * Time: 2020/8/20 20:29
 */
public class EdiltActivity extends AppCompatActivity {


    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int ALBUM_REQUEST_CODE = 2;
    private static final int CROP_SMALL_PICTURE = 3;
    private TextView pjnumtv;
    private TextView projectnumed;
    private TextView projectname;
    private TextView pjnametv;
    private TextView operatortime;
    private TextView operatortimetv;
    private TextView operatorname;
    private EditText operatornameed;
    private TextView rectificationname;
    private EditText rectificationnameed;
    private TextView checkname;
    private EditText checknameed;
    private TextView isissue;
    private SwitchCompat issuesw;

    private boolean ischeck;
    private ImageView imageView;
    private Bitmap photo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.activtytheme);
        setContentView(R.layout.edilt_view);
        initialize();
        operatortimetv.setText(getCurrentDate());
    }


    public String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sf.format(d);
    }

    public boolean save() {
        Adminbean bean = new Adminbean();
        bean.setProject_name(pjnametv.getText().toString());
        bean.setOperator_time(operatortimetv.getText().toString());
        String checkname = checknameed.getText().toString();
        bean.setCheck_name(checkname);
        String operatorname = operatornameed.getText().toString();
        bean.setOperator_name(operatorname);
        String rectificationname = rectificationnameed.getText().toString();
        bean.setRectification_name(rectificationname);
        bean.setIssue(ischeck);
        if (!TextUtils.isEmpty(checkname) &&
                !TextUtils.isEmpty(rectificationname) &&
                !TextUtils.isEmpty(operatorname) && photo != null) {
            String base64 = Utils.bitmapToBase64(photo);
            bean.setPhoto(base64);
            DBdao.getInstance(this).insert1(bean);
            return true;
        } else {
            Toast.makeText(this, "有项目没填", Toast.LENGTH_SHORT).show();
            return false;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, Menu.NONE, "保存");
        menu.add(1, Menu.FIRST+1, Menu.NONE, "退出");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case Menu.FIRST:
                boolean save = save();
                if (save) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                break;
            case Menu.FIRST + 1:
                finish();
                break;
            default:
                break;
        }
        return true;

    }


    private void initialize() {
        pjnumtv = (TextView) findViewById(R.id.pj_num_tv);
        projectnumed = (TextView) findViewById(R.id.project_num_ed);
        projectname = (TextView) findViewById(R.id.project_name);
        pjnametv = (TextView) findViewById(R.id.pj_name_tv);
        operatortime = (TextView) findViewById(R.id.operator_time);
        operatortimetv = (TextView) findViewById(R.id.operator_time_tv);
        operatortimetv = (TextView) findViewById(R.id.operator_time_tv);
        operatorname = (TextView) findViewById(R.id.operator_name);
        operatornameed = (EditText) findViewById(R.id.operator_name_ed);
        operatornameed = (EditText) findViewById(R.id.operator_name_ed);
        rectificationname = (TextView) findViewById(R.id.rectification_name);
        rectificationnameed = (EditText) findViewById(R.id.rectification_name_ed);
        rectificationnameed = (EditText) findViewById(R.id.rectification_name_ed);
        checkname = (TextView) findViewById(R.id.check_name);
        checknameed = (EditText) findViewById(R.id.check_name_ed);
        checknameed = (EditText) findViewById(R.id.check_name_ed);
        isissue = (TextView) findViewById(R.id.is_issue);
        issuesw = (SwitchCompat) findViewById(R.id.issue_sw);

        issuesw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischeck = isChecked;
                if (isChecked) {
                    Toast.makeText(EdiltActivity.this, "当前项目有问题", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EdiltActivity.this, "当前项目没问题", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button select_bt = findViewById(R.id.select_bt);
        select_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        imageView = findViewById(R.id.image);
    }


    private class DialogOnCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            dialogInterface.dismiss();
        }
    }


    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EdiltActivity.this);
        alertDialog.setOnCancelListener(new DialogOnCancelListener());
        alertDialog.setTitle("请选择操作");
        // gallery, camera.
        // 注意这里改了记得改回来，多加了一个摄像
        String[] options = {"拍照", "相册"};
        alertDialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.CAMERA}, 1000);
                                    return;
                                }
                            }
                            getPicFromCamera();

                        } else if (which == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
                                    return;
                                }
                            }
                            getPicFromAlbm();

                        }
                    }
                }
        );
        alertDialog.show();
    }


    /**
     * 从相机获取图片
     */
    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        File tempFile = new File(Environment.getExternalStorageDirectory().getPath(), File.separator + "test" + File.separator + "test001.png");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("getPicFromCamera", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
//        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        File out = new File(getPath());
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    //裁剪后的地址
    public String getPath() {
        //resize image to thumb
        String mFile = null;
        if (mFile == null) {
            mFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "outtest001.png";
        }
        return mFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        File tempFile = new File(Environment.getExternalStorageDirectory().getPath(), File.separator + "test" + File.separator + "test001.png");
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", tempFile);
                        startPhotoZoom(contentUri);//开始对图片进行裁剪处理
                    } else {
                        startPhotoZoom(Uri.fromFile(tempFile));//开始对图片进行裁剪处理
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    startPhotoZoom(uri); // 开始对图片进行裁剪处理
                }
                break;
            case CROP_SMALL_PICTURE:  //调用剪裁后返回
                if (intent != null) {
                    // 让刚才选择裁剪得到的图片显示在界面上
                    photo = BitmapFactory.decodeFile(getPath());

                    imageView.setImageBitmap(photo);
                } else {
                    Log.e("data", "data为空");
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    switch (grantResult) {
                        case PackageManager.PERMISSION_GRANTED://同意授权0
                            getPicFromCamera();
                            break;
                        case PackageManager.PERMISSION_DENIED://拒绝授权-1
                            Toast.makeText(EdiltActivity.this, "相关权限没授权", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else if (requestCode == 1001) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    switch (grantResult) {
                        case PackageManager.PERMISSION_GRANTED://同意授权0
                            getPicFromAlbm();
                            break;
                        case PackageManager.PERMISSION_DENIED://拒绝授权-1
                            Toast.makeText(EdiltActivity.this, "相关权限没授权", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
