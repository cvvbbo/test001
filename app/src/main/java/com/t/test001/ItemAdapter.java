package com.t.test001;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: xiong
 * Time: 2020/8/20 17:57
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {


    ArrayList<Adminbean> datas = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
       if (datas.get(position).getIssue()){
           holder.issue_tv.setText("是");
       }else {
           holder.issue_tv.setText("否");
       }
       holder.operator_name_tv.setText(datas.get(position).getOperator_name());
       holder.project_name_tv.setText(datas.get(position).getProject_name());

    }


    public void setDatas(ArrayList<Adminbean> datas) {
        this.datas = datas;

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView operator_name_tv;
        private TextView project_name_tv;
        private TextView issue_tv;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            operator_name_tv = itemView.findViewById(R.id.operator_name);
            project_name_tv = itemView.findViewById(R.id.project_name);
            issue_tv = itemView.findViewById(R.id.issue_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClick != null) {
                        mOnItemClick.itemCilck(getAdapterPosition());
                    }
                }
            });
        }
    }

    OnItemClick mOnItemClick;

    public void setmOnItemClickListener(OnItemClick mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }


    interface OnItemClick {
        void itemCilck(int position);
    }
}
