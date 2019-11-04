package com.example.facetokimageuploaddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

    //widget
    private ImageView imageView;
    private View view;
    //variables

    //    private String stringSizeOfArray;
    private ArrayList<UserModel> arrayListSelectedImages;
    private Context context;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(ArrayList<UserModel> arrayListSelectedImages, Context context) {
        this.arrayListSelectedImages = arrayListSelectedImages;
        this.context = context;
//        stringSizeOfArray= String.valueOf(arrayListUserModel.size());
    }


    @Override
    public int getCount() {
        return arrayListSelectedImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.recycler_item_selected_iamge, null);

        getIdForAllVariables(view);

        Picasso.with(context)
                .load("file://" + arrayListSelectedImages.get(position).getImagesPath())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.recycle_placeholder)
                .into(imageView);

        view.setOnClickListener(this);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    private void getIdForAllVariables(View view) {
        imageView = view.findViewById(R.id.image_view_recycler_item_selected_image);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View v = (View) object;
        viewPager.removeView(v);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "we are working on this functionality", Toast.LENGTH_SHORT).show();
    }
}
