package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

;import com.fozechmoblive.fluidwallpaper.livefluid.R;

public class ViewPagerArrowIndicator extends LinearLayout {
    private ImageView mRightArrow;
    private ImageView mLeftArrow;
    private ViewPager mViewPager;

    public ViewPagerArrowIndicator(Context context) {
        super(context);
        init(context);
    }

    public ViewPagerArrowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(HORIZONTAL);
        mLeftArrow = new ImageView(context);
        mRightArrow = new ImageView(context);

        // Thay đổi kích thước của mLeftArrow và mRightArrow
        int desiredWidth = 100;  // Đặt kích thước mong muốn ở đây
        int desiredHeight = 50;  // Đặt kích thước mong muốn ở đây

        // Đặt kích thước mới cho mLeftArrow
        ViewGroup.LayoutParams leftArrowLayoutParams = new ViewGroup.LayoutParams(desiredWidth, desiredHeight);
        mLeftArrow.setLayoutParams(leftArrowLayoutParams);

        // Đặt kích thước mới cho mRightArrow
        ViewGroup.LayoutParams rightArrowLayoutParams = new ViewGroup.LayoutParams(desiredWidth, desiredHeight);
        mRightArrow.setLayoutParams(rightArrowLayoutParams);

        setArrowIndicatorRes(R.drawable.ic_arrow_left, R.drawable.ic_arrow_right);
    }

    private void setParams() {
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void bind(ViewPager viewPager) {
        mViewPager = viewPager;

        mLeftArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFirstPage()) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            }
        });

        mRightArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLastPage()) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handleVisibility();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        arrange();
    }

    private void handleVisibility() {
        if (isFirstPage()) {
            mLeftArrow.setVisibility(INVISIBLE);
        } else {
            mLeftArrow.setVisibility(VISIBLE);
        }

        if (isLastPage()) {
            mRightArrow.setVisibility(INVISIBLE);
        } else {
            mRightArrow.setVisibility(VISIBLE);
        }
    }

    private void arrange() {
        setParams();
        View view = getChildAt(0);
        removeViewAt(0);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.weight = 1;
        view.setLayoutParams(layoutParams);
        addView(mLeftArrow);
        addView(view);
        addView(mRightArrow);
        handleVisibility();
    }

    private boolean isLastPage() {
        return mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1;
    }

    private boolean isFirstPage() {
        return mViewPager.getCurrentItem() == 0;
    }

    public void setArrowIndicatorRes(int leftArrowResId, int rightArrowResId) {
        mLeftArrow.setImageResource(leftArrowResId);
        mRightArrow.setImageResource(rightArrowResId);
    }
}