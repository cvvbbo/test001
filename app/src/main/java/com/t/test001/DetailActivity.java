package com.t.test001;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

/**
 * Author: xiong
 * Time: 2020/8/20 23:09
 */
public class DetailActivity extends AppCompatActivity {


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
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.activtytheme);
        setContentView(R.layout.edilt_view);
        initialize();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        if (position != -1) {
            Adminbean bean = DBdao.getInstance(DetailActivity.this).queryid(String.valueOf(position + 1));
            operatornameed.setText(bean.getOperator_name());
            operatortimetv.setText(bean.getOperator_time());
            rectificationnameed.setText(bean.getRectification_name());
            checknameed.setText(bean.getCheck_name());
            Bitmap bitmap = Utils.base64ToBitmap(bean.getPhoto());
            image.setImageBitmap(bitmap);
            issuesw.setChecked(bean.getIssue());
        }
    }

    private void initialize() {
        pjnumtv = (TextView) findViewById(R.id.pj_num_tv);
        projectnumed = (TextView) findViewById(R.id.project_num_ed);
        projectname = (TextView) findViewById(R.id.project_name);
        pjnametv = (TextView) findViewById(R.id.pj_name_tv);
        operatortime = (TextView) findViewById(R.id.operator_time);
        operatortimetv = (TextView) findViewById(R.id.operator_time_tv);
        operatorname = (TextView) findViewById(R.id.operator_name);
        operatornameed = (EditText) findViewById(R.id.operator_name_ed);
        operatornameed.setFocusable(false);
        operatornameed.setFocusableInTouchMode(false);
        rectificationname = (TextView) findViewById(R.id.rectification_name);
        rectificationnameed = (EditText) findViewById(R.id.rectification_name_ed);
        rectificationnameed.setFocusable(false);
        rectificationnameed.setFocusableInTouchMode(false);
        checkname = (TextView) findViewById(R.id.check_name);
        checknameed = (EditText) findViewById(R.id.check_name_ed);
        checknameed.setFocusable(false);
        checknameed.setFocusableInTouchMode(false);
        isissue = (TextView) findViewById(R.id.is_issue);
        issuesw = (SwitchCompat) findViewById(R.id.issue_sw);
        issuesw.setClickable(false);

        Button select_bt = findViewById(R.id.select_bt);
        select_bt.setVisibility(View.GONE);
        image = findViewById(R.id.image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, Menu.NONE, "退出");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case Menu.FIRST:
                finish();
                break;
            default:
                break;
        }
        return true;

    }



}
