package com.example.facetokimageuploaddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

public class PostIt extends AppCompatActivity {

    //Widgets
    private ViewPager viewPager;

    //Variables
    private int ITEM_COUNT;
    private ViewPagerAdapterUploadImages viewPagerAdapterUploadImages;
    private ArrayList<UserModel> arrayListUserModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_it);
        arrayListUserModels = (ArrayList<UserModel>) getIntent().getSerializableExtra("IMAGES_ARRAY");

        getIdForAllWidgets();

        viewPagerAdapterUploadImages = new ViewPagerAdapterUploadImages(arrayListUserModels, this);
        viewPager.setAdapter(viewPagerAdapterUploadImages);

        ITEM_COUNT = viewPagerAdapterUploadImages.getCount();

    }

    private void getIdForAllWidgets() {
        viewPager = findViewById(R.id.view_pager_post_it);
    }
}
