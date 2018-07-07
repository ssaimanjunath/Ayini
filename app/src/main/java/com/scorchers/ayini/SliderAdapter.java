package com.scorchers.ayini;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ssaim on 14-03-2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context = context;
    }

    //image for sliders
    public int[] slide_images = {
            R.drawable.hey,
            R.drawable.ic_info_outline_white_24dp,
            R.drawable.ic_shovel_and_ground,
            R.drawable.ic_test
    };

    public String[] slide_headings = {
            "Hi, There!!!",
            "INFO ICON",
            "ENTER YOUR VALUES",
            "RESULTS ARRIVE"
    };

    public String[] slide_descs = {
            "Let us take you on a tour around the app's features. Just sit back and swipe!",
            "Click these to know more.. As simple as that",
            "pH, Nitrogen, Potasium, Magnesium blah blah blah... You enter them all here!",
            "Wouldn't it be fascinating to know what are suitable crops for your field/garden?"

    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImagView = view.findViewById(R.id.slideImage);
        TextView slideHeading = view.findViewById(R.id.side_heading);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

        slideImagView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }
}
