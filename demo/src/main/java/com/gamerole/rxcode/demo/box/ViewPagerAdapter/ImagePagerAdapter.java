package com.gamerole.rxcode.demo.box.ViewPagerAdapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eqdd.common.utils.ImageUtil;
import com.gamerole.rxcode.demo.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author吕志豪 .
 * @date 17-12-25  下午2:09.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
public class ImagePagerAdapter extends PagerAdapter {
    private Context mContext;
    LinkedList<View> mCache = new LinkedList<>();
    HashMap<Integer, String> mImages;
    public ImagePagerAdapter(Context pContext, HashMap<Integer, String> mImages ){
        this.mContext = pContext;
        this.mImages=mImages;
    }



    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container,final int position) {
        View convertView ;
        ViewHolder mHolder;
        if(mCache.size() == 0){
            convertView = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.list_item_tantan,null,false).getRoot();
            mHolder = new ViewHolder();
            mHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(mHolder);
        }else{
            convertView = mCache.removeFirst();
            mHolder = (ViewHolder) convertView.getTag();
        }
        ImageUtil.setImage(mImages.get(position%8),mHolder.imageView);
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(mCache.size() > 0){
            mCache.clear();
        }
        container.removeView((View)object);
        mCache.add((View)object);
    }

    private class ViewHolder{
        ImageView imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}