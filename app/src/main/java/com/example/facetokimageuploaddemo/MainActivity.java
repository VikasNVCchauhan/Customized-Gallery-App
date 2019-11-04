package com.example.facetokimageuploaddemo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //widget
    private ImageView imageViewBackButton;
    private Spinner spinner;

    //variables
    private ArrayList<UserModel> arrayListUserModelDirectoriesAndImages;
    private ArrayList<UserModel> arrayListUserModelMergerList;
    //    private int DOT_COUNT;
    private ArrayList<String> arrayListAllImages;
    //    private ArrayList<String> directoryPath, directoryName;
    private ArrayList<String> arrayListStringDirectoriesNameSpinner;
    //    private FilePath filePath;
    private boolean boolean_folder;
    //constants
    private int PREVIOUS_VALUE;
    private static final int INDEX = 0;
    private static final String ALL_IMAGES = "Gallery ";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayListUserModelDirectoriesAndImages = new ArrayList<>();
        arrayListUserModelMergerList = new ArrayList<>();
        arrayListStringDirectoriesNameSpinner = new ArrayList<>();
        arrayListAllImages = new ArrayList<>();

        arrayListStringDirectoriesNameSpinner.add("Gallery Images");
        getIdForAllVariables();
        //setViewPagerSelectedImages(arrayListUserModelInitializedAllImages.get(0).getArrayListImageCollection());
        //------- this method is called after setting all data in recycler view and view pager
        //-------Actually this method is for getting all directories from phone
        //------- All directories are set to the spinner -------//
        getSpinnerDirectories();
        setDataToSpinner();
        //------- here we are setting the required listeners -------//
        imageViewBackButton.setOnClickListener(this);
    }

    //------- data is setting to the spinner -------//
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setDataToSpinner() {

        ArrayAdapter arrayAdapterSpinner = new ArrayAdapter(this, R.layout.spinner_item_main, arrayListStringDirectoriesNameSpinner) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextSize(17);
                tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                return tv;
            }

        };
        spinner.setDropDownVerticalOffset(100);

        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout_view_recycler_view, new RecyclerFragment(arrayListUserModelDirectoriesAndImages, i)).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //------- Spinner Directories --------//
    private ArrayList<UserModel> getSpinnerDirectories() {
        //       ArrayList<String> cameraFolderImages = new ArrayList<>();
        arrayListUserModelDirectoriesAndImages.clear();
        arrayListAllImages.clear();

        Uri uri;
        Cursor cursor;
        int int_position = 0;

        int column_index_data, column_index_folder_name;
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < arrayListUserModelDirectoriesAndImages.size(); i++) {
                if (arrayListUserModelDirectoriesAndImages.get(i).getDirectoriesName().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }

            if (boolean_folder) {
                ArrayList<String> imagesPath = new ArrayList<>();
                imagesPath.addAll(arrayListUserModelDirectoriesAndImages.get(int_position).getArrayListImageCollection());
                imagesPath.add(absolutePathOfImage);
                arrayListAllImages.add(absolutePathOfImage);
                arrayListUserModelDirectoriesAndImages.get(int_position).setArrayListImageCollection(imagesPath);

            } else {
                ArrayList<String> imagesPath = new ArrayList<>();
                imagesPath.add(absolutePathOfImage);
                UserModel userModel = new UserModel();
                arrayListAllImages.add(absolutePathOfImage);
                userModel.setDirectoriesName(cursor.getString(column_index_folder_name));
                arrayListStringDirectoriesNameSpinner.add(userModel.getDirectoriesName());
                userModel.setArrayListImageCollection(imagesPath);
                arrayListUserModelDirectoriesAndImages.add(userModel);
            }
        }

        UserModel userModel = new UserModel();
        userModel.setArrayListImageCollection(arrayListAllImages);
        userModel.setDirectoriesName("Gallery Images");
        arrayListUserModelDirectoriesAndImages.add(0, userModel);
        return arrayListUserModelDirectoriesAndImages;

    }

    //------- Spinner Directories --------//
//    private ArrayList<UserModel> getAllImagesOfDirectories() {           //For All Images
//        ArrayList<File> allFolderImages = new ArrayList<>();
//        arrayListUserModelInitializedAllImages.clear();
//
//        int column_index_data, column_index_folder_name;
//
//        String absolutePathOfImage = null;
//        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
//        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
//        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
//
//        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//        while (cursor.moveToNext()) {
//
//            absolutePathOfImage = cursor.getString(column_index_data);
//
//            //Toast.makeText(this, "" + absolutePathOfImage, Toast.LENGTH_SHORT).show();
//            Log.e("Column", absolutePathOfImage);
//            Log.e("Folder", cursor.getString(column_index_folder_name));
//
//            allFolderImages.add(new File(absolutePathOfImage));
//        }
//
//        userModel.setDirectoriesName("All Images");
//        userModel.setArrayListImageCollection(allFolderImages);
//        arrayListUserModelInitializedAllImages.add(userModel);
//
//        return arrayListUserModelInitializedAllImages;
//    }

//    //------- Here we are calling recycler view --------//
//    private void getInitializedDirectoryPath() {
//        if (position > 0) {
//            if(arrayListImageFilesFromSpinner.size()<20){
//                arrayListImageFilesFromSpinner=(ArrayList<UserModel>) arrayListInitializedImages.clone();
//                setRecycleView(arrayListImageFilesFromSpinner);
//                arrayListUserModelViewPager.subList(1, arrayListImageFilesFromSpinner.size()).clear();
//            }
//            else{
//                setRecycleView(arrayListImageFilesFromSpinner);
//                arrayListUserModelViewPager.subList(1, arrayListImageFilesFromSpinner.size()).clear();
//            }

//        }else if(position==0) {
//        directoryPath.clear();
//        directoryPath = SearchFile.getDirectoriesPath(filePath.DCIM);
//        if (Environment.getExternalStorageState() != null) {
//            if (SearchFile.getDirectoriesPath(filePath.PICTURES) != null) {
//                directoryPath = SearchFile.getDirectoriesPath(filePath.PICTURES);
//                directoryName = SearchFile.getDirectoriesName();
//            }
//        }

//        recyclerViewAdapterVertical = new RecyclerViewAdapterVertical(this, getInitializedImages());
//        recyclerViewAllImages.setAdapter(recyclerViewAdapterVertical);
//
//
//        //-------------Here we set view pager adapter ---------------//
//        arrayListUserModelUserModelViewPager = (ArrayList<UserModel>) arrayListUserModelInitializedAllImages.clone();
//        arrayListUserModelUserModelViewPager.subList(1, arrayListUserModelUserModelViewPager.size()).clear();
////        }
//
//        viewPagerAdapter = new ViewPagerAdapter(arrayListUserModelUserModelViewPager, this);
//        viewPager.setAdapter(viewPagerAdapter);
//
//        DOT_COUNT = viewPagerAdapter.getCount();
//        textViewTotalCount.setText(String.valueOf(DOT_COUNT));
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                textViewCurrentLoc.setText(String.valueOf(i + CURRENT_LOC));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
//    }

    //   private void setRecycleView(ArrayList<UserModel> arrayListImageFilesFromSpinner) {
//        recyclerViewAdapterVertical = new RecyclerViewAdapterVertical(this, arrayListImageFilesFromSpinner);
//        recyclerViewAllImages.setAdapter(recyclerViewAdapterVertical);
//    }

//    private ArrayList<UserModel> getInitializedImages() {
//        arrayListUserModelInitializedAllImages.clear();
//        for (int i = 0; i < directoryPath.size(); i++) {
//            if (directoryPath.get(i).length() != NULL_VALUE) {
//                arrayListUserModelInitializedAllImages = SearchFile.getDirectoriesImages(directoryPath.get(i), directoryName.get(i));
//            }
//        }
//        return arrayListUserModelInitializedAllImages;
//    }

    private void getIdForAllVariables() {
        spinner = findViewById(R.id.spinner_all_directories);
        imageViewBackButton = findViewById(R.id.image_back_tool_bar);
    }

    //-------Set listener to the widget ------//
    @Override
    public void onClick(View view) {
        if (view == imageViewBackButton) {
            Toast.makeText(this, "We are working on it....", Toast.LENGTH_SHORT).show();
        }
    }
}
