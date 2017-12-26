package com.eqdd.common.adapter.slimadapter;

/**
 * Created by linshuaibin on 16/05/2017.
 */
public class SlimAdapterExReverse<D> extends SlimAdapterEx<D> {
    public static SlimAdapterExReverse create() {
        return new SlimAdapterExReverse();
    }

    @Override
    public final void onBindViewHolder(SlimViewHolder holder, int position) {
        holder.bind(getItem(position), position % getData().size());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position % getData().size());
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position % getData().size());
    }
}
