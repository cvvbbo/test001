package com.t.test001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aa();
    }


    public void haha(View view) {
//        Adminbean bean = new Adminbean();
//        bean.setProject_name("aaa");
//        bean.setRectification_name("66");
//        bean.setOperator_name("bbb");
//        bean.setOperator_time("cc");
//        bean.setCheck_name("dd");
//        bean.setIssue(false);
//        bean.setPhoto("111");
//        DBdao.getInstance(this).insert1(bean);

    }


    public void aa() {
       // for (int j = 0; j < 3; j++) {
         //   Log.e("aa---->j=", j + "");
            for (int i = 0; i < 5; i++) {
                if (i == 3) {
                    Log.e("bb--->i=", i + "");
                   // break;
                     return;
                }
                Log.e("cc-->i=", i + "");
            }
      //  }
    }
}
