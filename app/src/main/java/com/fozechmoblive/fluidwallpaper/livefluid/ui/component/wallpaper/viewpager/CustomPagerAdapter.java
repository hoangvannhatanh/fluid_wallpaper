package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

;import com.fozechmoblive.fluidwallpaper.livefluid.R;

public class CustomPagerAdapter extends PagerAdapter {

    String[] mResources;


    Context mContext;

    LayoutInflater mLayoutInflater;


    public CustomPagerAdapter(Context context, String[] listResource) {
        this.mResources = listResource;
        mContext = context;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override

    public int getCount() {

        return mResources.length;

    }


    @Override

    public boolean isViewFromObject(View view, Object object) {

        return view == object;

    }


    @Override

    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item_viewpager, container, false);

        TextView textItem = itemView.findViewById(R.id.text_item);

        textItem.setText(mResources[position]);

        container.addView(itemView);


        return itemView;

    }

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((TextView) object);

    }

}