package com.teamhelper.glass.view.adapter;


import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yanchenglong
 * @time 2022/6/30
 */
public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public BaseRecyclerAdapter() {
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void refresh(int position) {
        notifyItemChanged(position);
    }
}
