package com.example.facetokimageuploaddemo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends Fragment implements View.OnClickListener, RecyclerViewAdapterVertical.OnItemClickListenerRecycleItems {


    //Widgets
    private AppCompatActivity appCompatActivity;
    private RecyclerView recyclerView;
    private View view;
    private TextView textView;

    //Variables
    private String size;
    private ArrayList<UserModel> arrayListUserModelRecyclerData;
    private ArrayList<UserModel> arrayListFilePathViewPager;
    private ArrayList<String> arrayListStringFilePathRecycle;
    private ArrayList<UserModel> arrayListUserModelFilePathRecycle;
    private RecyclerViewAdapterVertical recyclerViewAdapterVertical;
    private ArrayList<UserModel> arrayListUserModelSelectedItems;
    private int int_position;

    //Constants
    private final static int INDEX = 0;

    public RecyclerFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public RecyclerFragment(ArrayList<UserModel> arrayListUserModelRecyclerData, int int_position) {
        this.arrayListUserModelRecyclerData = arrayListUserModelRecyclerData;
        this.int_position = int_position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recycler, container, false);
        appCompatActivity = (AppCompatActivity) getContext();
        getIdForAllWidgets(view);

        arrayListUserModelFilePathRecycle = new ArrayList<>();
        arrayListFilePathViewPager = new ArrayList<>();
        arrayListStringFilePathRecycle = new ArrayList<>();
        arrayListUserModelSelectedItems = new ArrayList<>();


        /*
        Here we are setting data of array list that is coming from selecting spinner directories to the
        new array list in new array list we are we are also setting the
         */
        setDataForRecyclerArrayList();

        setDataForViewPagerArrayList();

        size = String.valueOf(arrayListUserModelRecyclerData.get(int_position).getArrayListImageCollection().size());
        textView.setText(size);


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerViewAdapterVertical = new RecyclerViewAdapterVertical(appCompatActivity, arrayListUserModelFilePathRecycle, this, arrayListUserModelSelectedItems);
        recyclerView.setAdapter(recyclerViewAdapterVertical);

        callingViewPager(arrayListFilePathViewPager);
        return view;
    }

    private void setDataForViewPagerArrayList() {
        UserModel userModel = new UserModel();
        userModel.setImagesPath(arrayListUserModelFilePathRecycle.get(0).getImagesPath());
        userModel.setPosition(arrayListUserModelFilePathRecycle.get(0).getPosition());
        arrayListFilePathViewPager.add(userModel);
    }

    private void setDataForRecyclerArrayList() {
        for (int i = 0; i < arrayListUserModelRecyclerData.get(int_position).getArrayListImageCollection().size(); i++) {
            UserModel innerUserModel = new UserModel();
            innerUserModel.setSelect(false);
            innerUserModel.setDirectoriesName(arrayListUserModelRecyclerData.get(int_position).getDirectoriesName());
            innerUserModel.setImagesPath(arrayListUserModelRecyclerData.get(int_position).getArrayListImageCollection().get(i));
            arrayListUserModelFilePathRecycle.add(innerUserModel);
        }
    }

    private void getIdForAllWidgets(View view) {
        recyclerView = view.findViewById(R.id.recyclerview_view_all_images_main_activity);
        textView = view.findViewById(R.id.text_view_total_count);
    }

    @Override
    public void onClick(View view) {
    }

    private void callingViewPager(ArrayList<UserModel> arrayList) {
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout_view_pager_fragment, new ViewPagerFragment(arrayList)).commit();
    }

    @Override
    public void onRecyclerItemClicked(int position, ArrayList<UserModel> arrayList) {
        if (arrayList.size() == 0) {
            callingViewPager(arrayListFilePathViewPager);
        } else {
            callingViewPager(arrayList);
        }
    }

    @Override
    public void onRecyclerItemLongClick(int position, ArrayList<UserModel> arrayList) {
        if (arrayList.size() == 0) {
            callingViewPager(arrayListFilePathViewPager);
        } else {
            callingViewPager(arrayList);
        }
    }
}
