package com.example.facetokimageuploaddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerAdapterUploadImages extends PagerAdapter {

    private ImageView imageView;
    private View view;
    private ArrayList<UserModel> arrayListAllImages;
    private Context context;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapterUploadImages(ArrayList<UserModel> arrayListAllImages, Context context) {
        this.arrayListAllImages = arrayListAllImages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListAllImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_upload_images_view_pagers, null);

        getIdForWidget(view);
        Picasso.with(context)
                .load("file://" + arrayListAllImages.get(position).getImagesPath())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.recycle_placeholder)
                .into(imageView);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View v = (View) object;
        viewPager.removeView(v);
    }

    private void getIdForWidget(View view) {
        imageView = view.findViewById(R.id.image_view_upload_image_view_pager);
    }
}
