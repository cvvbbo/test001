package com.t.test001;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: xiong
 * Time: 2020/8/20 17:55
 */
public class ItemActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private TextView tv;
    private List<Adminbean> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.activtytheme);
        setContentView(R.layout.item_view_activity);
        recyclerView = findViewById(R.id.ry);
        tv = findViewById(R.id.tv);
        datas = DBdao.getInstance(this).query1();
        if (datas.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter();

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.bottom = 20;
            }
        });
        adapter.setDatas((ArrayList<Adminbean>) datas);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new ItemAdapter.OnItemClick() {
            @Override
            public void itemCilck(int position) {
                //  Log.e("position",position+"");
                //  DBdao.getInstance(ItemActivity.this).queryid(String.valueOf(position+1));

                Intent intent = new Intent(ItemActivity.this, DetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, Menu.NONE, "添加");
        menu.add(1, Menu.FIRST + 1, Menu.NONE, "退出");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case Menu.FIRST:
                startActivityForResult(new Intent(this, EdiltActivity.class), 100);
                break;
            case Menu.FIRST + 1:
                finish();
                break;
            default:
                break;
        }
        return true;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                List<Adminbean> newdatas = DBdao.getInstance(this).query1();
                if (newdatas.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    tv.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                }
                adapter.setDatas((ArrayList<Adminbean>) newdatas);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DataDiffCallback(datas, newdatas));
                diffResult.dispatchUpdatesTo(adapter);
                // todo 因为有了新数据，同时需要对旧数据进行及时的更新操作。（不然会抛出异常）
                datas.clear();
                datas.addAll(newdatas);
                // adapter.notifyDataSetChanged();
            }
        }
    }
}
