package com.t.test001;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: xiong
 * Time: 2020/8/20 12:14
 */
public class DBdao {

    private static DBdao db;
    private Context context;


    private DBdao(Context context) {
        this.context = context;
    }


    public static DBdao getInstance(Context context) {
        if (db == null) {
            synchronized (DBdao.class) {
                if (db == null) {
                    db = new DBdao(context);
                }
                return db;
            }
        }
        return db;
    }

    private SQLiteDatabase getConn(Context context) {
        SQLiteDatabase database = null;
        try {
            database = new MyDbhelp(context).getWritableDatabase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return database;
    }


    public void insert1(Adminbean bean) {
        SQLiteDatabase conn = null;
        try {
            conn = getConn(context);
            ContentValues values = new ContentValues();
            values.put(MyDbhelp.project_name, bean.getProject_name());
            values.put(MyDbhelp.operator_name, bean.getOperator_name());
            values.put(MyDbhelp.rectification_name, bean.getRectification_name());
            values.put(MyDbhelp.check_name, bean.getCheck_name());
            values.put(MyDbhelp.operator_time, bean.getOperator_time());
            values.put(MyDbhelp.issue, String.valueOf(bean.getIssue()));
            values.put(MyDbhelp.photo, bean.getPhoto());
            long insert = conn.insert(MyDbhelp.table_name, null, values);
            if (insert > 0) {
                Log.e("DBdao", "insert success!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

    }

    public List<Adminbean> query1() {
        SQLiteDatabase conn = null;
        Cursor cursor = null;
        List<Adminbean> datas = new ArrayList<>();
        try {
            conn = getConn(context);
            cursor = conn.query(MyDbhelp.table_name, new String[]{"*"}, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Adminbean bean = new Adminbean();
                    bean.setProject_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.project_name)));
                    bean.setOperator_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.operator_name)));
                    bean.setRectification_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.rectification_name)));
                    bean.setCheck_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.check_name)));
                    bean.setOperator_time(cursor.getString(cursor.getColumnIndex(MyDbhelp.operator_time)));
                    bean.setIssue(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(MyDbhelp.issue))));
                    bean.setPhoto(cursor.getString(cursor.getColumnIndex(MyDbhelp.photo)));
                    datas.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return datas;
    }

    public Adminbean queryid(String id) {
        SQLiteDatabase conn = null;
        Cursor cursor = null;
        Adminbean bean = null;
        try {
            conn = getConn(context);
            cursor = conn.query(MyDbhelp.table_name, new String[]{"*"}, "_id=?", new String[]{id}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    bean = new Adminbean();
                    bean.setProject_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.project_name)));
                    bean.setOperator_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.operator_name)));
                    bean.setRectification_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.rectification_name)));
                    bean.setCheck_name(cursor.getString(cursor.getColumnIndex(MyDbhelp.check_name)));
                    bean.setOperator_time(cursor.getString(cursor.getColumnIndex(MyDbhelp.operator_time)));
                    bean.setIssue(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(MyDbhelp.issue))));
                    bean.setPhoto(cursor.getString(cursor.getColumnIndex(MyDbhelp.photo)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return bean;
    }


}
