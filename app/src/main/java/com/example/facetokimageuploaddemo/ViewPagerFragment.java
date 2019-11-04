package com.example.facetokimageuploaddemo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment implements View.OnClickListener {


    //Widgets

    private LinearLayout linearLayoutCameraOptionFloatingButton, linearLayoutUploadOptionFloatingButton, linearLayoutCollectionOptionFloatingButton;
    private AppCompatActivity appCompatActivity;
    private TextView textViewCurrentLoc, textViewTotalCount;
    private ViewPager viewPager;
    private View view;
    private Uri uri;
    //Variables
    private RecyclerViewAdapterVertical recyclerViewAdapterVertical;
    private ArrayList<UserModel> arrayListUserModelViewPager;
    private ArrayList<UserModel> arrayListSelectedImages;
    private ViewPagerAdapter viewPagerAdapter;
    private int DOT_COUNT;
    //Constants
    private static final int CURRENT_LOC = 1;
    private static final int CAMERA_REQUEST_CODE = 10;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ViewPagerFragment(ArrayList<UserModel> arrayListSelectedImages) {
        this.arrayListSelectedImages = arrayListSelectedImages;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        appCompatActivity = (AppCompatActivity) getContext();

        getIdForAllWidgets(view);
        arrayListUserModelViewPager = new ArrayList<>();

        setViewPagerSelectedImages(arrayListSelectedImages);

        //------- here we are setting the required listeners -------//
//        linearLayoutCameraOptionFloatingButton.setOnClickListener(this);
//        linearLayoutUploadOptionFloatingButton.setOnClickListener(this);
//        linearLayoutCollectionOptionFloatingButton.setOnClickListener(this);
        return view;
    }

    private void getIdForAllWidgets(View view) {
        viewPager = view.findViewById(R.id.viewPager_selected_image);
        textViewCurrentLoc = view.findViewById(R.id.text_view_counting_number);
        textViewTotalCount = view.findViewById(R.id.text_view_total_counting_selected_images);
//        linearLayoutCameraOptionFloatingButton = view.findViewById(R.id.linear_layout_camera_option);
//        linearLayoutUploadOptionFloatingButton = view.findViewById(R.id.linear_layout_upload_images);
//        linearLayoutCollectionOptionFloatingButton = view.findViewById(R.id.linear_layout_select_collection_option);
    }

    //-------------Here we set view pager adapter ---------------//
    private void setViewPagerSelectedImages(ArrayList<UserModel> arrayListSelectedImages) {

        viewPagerAdapter = new ViewPagerAdapter(arrayListSelectedImages, appCompatActivity);
        viewPager.setAdapter(viewPagerAdapter);

        DOT_COUNT = viewPagerAdapter.getCount();
        textViewTotalCount.setText(String.valueOf(DOT_COUNT));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                textViewCurrentLoc.setText(String.valueOf(i + CURRENT_LOC));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == linearLayoutCameraOptionFloatingButton) {
            openCameraActivity();
        } else if (view == linearLayoutUploadOptionFloatingButton) {
            Intent intent = new Intent(appCompatActivity, PostIt.class);
            intent.putExtra("IMAGES_ARRAY", arrayListSelectedImages);
            startActivity(intent);
        } else if (view == linearLayoutCollectionOptionFloatingButton) {
            Toast.makeText(appCompatActivity, "Select Multiple items", Toast.LENGTH_SHORT).show();
        }
    }

    //---------calling camera activity--------//
    private void openCameraActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
        }
    }
}
