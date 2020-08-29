package com.t.test001;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

/**
 * Author: xiong
 * Time: 2020/8/21 17:24
 */
public class DataDiffCallback extends DiffUtil.Callback {

    private List<Adminbean> olddatas;
    private List<Adminbean> newdatas;

    public DataDiffCallback(List<Adminbean> olddatas, List<Adminbean> newdatas) {
        this.olddatas = olddatas;
        this.newdatas = newdatas;
    }

    @Override
    public int getOldListSize() {
        return olddatas == null ? 0 : olddatas.size();
    }

    @Override
    public int getNewListSize() {
        return newdatas == null ? 0: newdatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        Adminbean oldbean = olddatas.get(oldItemPosition);
        Adminbean newbean = newdatas.get(newItemPosition);
        if (!oldbean.getPhoto().equals(newbean.getPhoto())){
            return false;
        }

        if (!oldbean.getCheck_name().equals(newbean.getPhoto())){
            return false;
        }

        if (!oldbean.getOperator_time().equals(newbean.getOperator_time())){
            return false;
        }

        if (!oldbean.getRectification_name().equals(newbean.getRectification_name())){
            return false;
        }

        return true;
    }
}
