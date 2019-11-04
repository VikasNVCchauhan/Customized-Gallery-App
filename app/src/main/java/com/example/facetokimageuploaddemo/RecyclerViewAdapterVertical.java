package com.example.facetokimageuploaddemo;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterVertical extends RecyclerView.Adapter<RecyclerViewAdapterVertical.VerticalViewHolder> {

    //Widget
    private OnItemClickListenerRecycleItems onItemClickListenerRecycleItems;
    private View view;
    private LayoutInflater layoutInflater;
    private Context context;

    //Variables
    private ArrayList<UserModel> arrayListUserModelSelectedItems = new ArrayList<>();
    private boolean LISTENER_SELECTION = false;
    private ArrayList<UserModel> arrayListImages;
    private boolean PREVIOUS_POSITION = true;

    //constant
    private final static int ONE = 1;
    //temp
    int count = 0;

    public RecyclerViewAdapterVertical(Context context, ArrayList<UserModel> arrayListImages, OnItemClickListenerRecycleItems onItemClickListenerRecycleItems, ArrayList<UserModel> arrayListUserModelSelectedItems) {
        this.context = context;
        this.arrayListImages = arrayListImages;
        this.onItemClickListenerRecycleItems = onItemClickListenerRecycleItems;
        this.arrayListUserModelSelectedItems = arrayListUserModelSelectedItems;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.recyclere_item_all_images, null);

        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder verticalViewHolder, int i) {


//        setSelectedImagesBoundaryVisibility(verticalViewHolder, i);
        UserModel userModel = arrayListImages.get(i);
        Picasso.with(context)
                .load("file://" + userModel.getImagesPath())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.recycle_placeholder)
                .into(verticalViewHolder.imageView);

        highLightSelectedImage(verticalViewHolder, i, userModel);

        UserModel item = arrayListImages.get(i);
        if (LISTENER_SELECTION) {
            verticalViewHolder.relativeLayoutImageCountBoundary.setVisibility(View.VISIBLE);
        }
    }
    private void highLightSelectedImage(VerticalViewHolder verticalViewHolder, int i, UserModel userModel) {


        if (userModel.isSelect()) {
            int position = -1;
            for (int j = 0; j < arrayListUserModelSelectedItems.size(); j++) {

                if (arrayListUserModelSelectedItems.get(j).getPosition() == i) {
                    position = j + 1;
//                    Toast.makeText(context, "match done "+arrayListUserModelSelectedItems.get(j).getPosition(), Toast.LENGTH_SHORT).show();
                }
            }

            if (!LISTENER_SELECTION) {
                verticalViewHolder.relativeLayoutImageCountBoundary.setVisibility(View.VISIBLE);
                settingRecyclerSelectedItemVisibility(position, verticalViewHolder);
            } else {
                settingRecyclerSelectedItemVisibility(position, verticalViewHolder);
            }
        } else {
            verticalViewHolder.relativeLayoutImageCountBoundary.setVisibility(View.GONE);
            verticalViewHolder.imageView.setColorFilter(Color.argb(0, 0, 0, 0));
            verticalViewHolder.textViewImageCount.setVisibility(View.GONE);
        }
    }

    private void settingRecyclerSelectedItemVisibility(int position, VerticalViewHolder verticalViewHolder) {
        verticalViewHolder.textViewImageCount.setVisibility(View.VISIBLE);
        verticalViewHolder.textViewImageCount.setText(String.valueOf(position));
        verticalViewHolder.imageView.setColorFilter(Color.argb(150, 0, 0, 10));
    }

    @Override
    public int getItemCount() {
        return arrayListImages.size();
    }

    class VerticalViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        public ImageView imageView;
        public TextView textViewImageCount;
        public RelativeLayout relativeLayoutImageCountBoundary;


        public VerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            getIdForAllVariables(itemView);

//            relativeLayoutImageCountBoundary.setVisibility(View.VISIBLE);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void getIdForAllVariables(View v) {
            imageView = v.findViewById(R.id.image_view_recycler_item_all_images);
            textViewImageCount = v.findViewById(R.id.text_view_selected_image_count_item_recycler_all_images);
            relativeLayoutImageCountBoundary = v.findViewById(R.id.relative_layout_selected_image_boundary_item_recycler_all_images);
        }

        @Override
        public boolean onLongClick(View view) {

            int position = getAdapterPosition();
            if (arrayListUserModelSelectedItems.size() == 0) {
                onLogPressMethod();
            } else {
                callingLongPressedListeners(position);
                //         Toast.makeText(context, "Size : " + arrayListUserModelSelectedItems.size(), Toast.LENGTH_SHORT).show();
                if (PREVIOUS_POSITION) {      //if new selected position is the same as previous one then continue this
//                    Toast.makeText(context, "Up size ", Toast.LENGTH_SHORT).show();
                    onLogPressMethod();
                } else {
                    if (arrayListUserModelSelectedItems.size() < 9) {
//                        Toast.makeText(context, "Under size " + arrayListUserModelSelectedItems.size(), Toast.LENGTH_SHORT).show();
                        onLogPressMethod();
                    } else {
                        Toast.makeText(context, "You Can't select more than 9 images " + arrayListUserModelSelectedItems.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            return false;
        }

        private void callingLongPressedListeners(int position) {
            for (int i = 0; i < arrayListUserModelSelectedItems.size(); i++) {
                if (arrayListUserModelSelectedItems.get(i).getPosition() == position) {
                    PREVIOUS_POSITION = true;
                    break;
                } else {
                    PREVIOUS_POSITION = false;
                }
            }
        }

        private void onLogPressMethod() {
            int positionLongPressed;
            int matchingPosition = -1;
            positionLongPressed = getAdapterPosition();   //getting the position of clicked item
            if (onItemClickListenerRecycleItems != null) {         //if selected items array isn't null them proceed further
                UserModel userModel = arrayListImages.get(positionLongPressed);
                if (userModel.isSelect()) {                        //if item is select then set select variable false so that we can use animation there
                    userModel.setSelect(false);

                    for (int i = 0; i < arrayListUserModelSelectedItems.size(); i++) {
                        if (arrayListUserModelSelectedItems.get(i).getPosition() == positionLongPressed) {        //here we sre removing the twice selected same item item
                            arrayListUserModelSelectedItems.remove(i);
                            matchingPosition = i;
                            if (arrayListUserModelSelectedItems.size() == 0) {
                                LISTENER_SELECTION = false;
                                break;
                            }
                        }
                    }
                    for (int i = matchingPosition; i < arrayListUserModelSelectedItems.size(); i++) {
                        notifyItemChanged(arrayListUserModelSelectedItems.get(i).getPosition());
                    }
                    checkSelectionOfClick();
                } else {
                    userModel.setSelect(true);
                    userModel.setPosition(positionLongPressed);                                             //here we are adding that position and image to array list
                    userModel.setImagesPath(arrayListImages.get(positionLongPressed).getImagesPath());
                    arrayListUserModelSelectedItems.add(userModel);
                    checkSelectionOfClick();
                }

                arrayListImages.set(positionLongPressed, userModel);
                onItemClickListenerRecycleItems.onRecyclerItemLongClick(positionLongPressed, arrayListUserModelSelectedItems);
            }

            notifyDataSetChanged();
            notifyItemChanged(positionLongPressed);
        }

        private void checkSelectionOfClick() {
            if (arrayListUserModelSelectedItems.size() > 0) {
                LISTENER_SELECTION = true;
            }
            if (arrayListUserModelSelectedItems.size() == 0) {
                LISTENER_SELECTION = false;
                return;
            }
        }

        @Override
        public void onClick(View view) {

            //showAllBoxes();

            int position = getAdapterPosition();
            if (LISTENER_SELECTION == false) {
                selectSingleItem();
            } else {
                callingLongPressedListeners(position);
                if (PREVIOUS_POSITION) {      //if new selected position is the same as previous one then continue this
                    Toast.makeText(context, "You can select mul because equal items founded", Toast.LENGTH_SHORT).show();
                    selectMultipleItems(position);
                } else {
                    if (arrayListUserModelSelectedItems.size() < 9) {
                        selectMultipleItems(position);
                    } else {
                        Toast.makeText(context, "You Can't select more than 9 images " + arrayListUserModelSelectedItems.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        private void selectMultipleItems(int position) {

            if (onItemClickListenerRecycleItems != null) {
                if (arrayListUserModelSelectedItems.size() == 0) {
                    LISTENER_SELECTION = false;
                    return;
                }
                if (onItemClickListenerRecycleItems != null) {


                    UserModel userModel = arrayListImages.get(position);
                    if (userModel.isSelect()) {
                        userModel.setSelect(false);

                        for (int i = 0; i < arrayListUserModelSelectedItems.size(); i++) {
                            if (arrayListUserModelSelectedItems.get(i).getPosition() == position) {        //here we sre removing the twice selected same item item
                                arrayListUserModelSelectedItems.remove(i);
                                if (arrayListUserModelSelectedItems.size() == 0) {
                                    LISTENER_SELECTION = false;
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < arrayListUserModelSelectedItems.size(); i++) {
                            notifyItemChanged(arrayListUserModelSelectedItems.get(i).getPosition());
                        }
                    } else {
                        userModel.setSelect(true);

                        userModel.setPosition(position);                                             //here we are adding that position and image to array list
                        userModel.setImagesPath(arrayListImages.get(position).getImagesPath());
                        arrayListUserModelSelectedItems.add(userModel);
                    }
                    arrayListImages.set(position, userModel);
                }
                notifyDataSetChanged();
                notifyItemChanged(position);
                onItemClickListenerRecycleItems.onRecyclerItemClicked(position, arrayListUserModelSelectedItems);
            }
        }


        //>>>>>>>>>>>>>>>>>>>>>>NO NEED TO CHANGE THE CODE <<<<<<<<<<<<<<<<<<<<<<<//
        //Single item select
        private void selectSingleItem() {

            int position;
            int selectedPosition = 0;
            position = getAdapterPosition();
            if (onItemClickListenerRecycleItems != null) {
                UserModel userModel = arrayListImages.get(position);
                userModel.setSelect(true);

                if (arrayListUserModelSelectedItems.size() > 0) {
                    //  Toast.makeText(context, "Deleting item", Toast.LENGTH_SHORT).show();
                    selectedPosition = arrayListUserModelSelectedItems.get(0).getPosition();
                    UserModel userModelSingleItemSelect = arrayListImages.get(selectedPosition);
                    userModelSingleItemSelect.setSelect(false);

                    if (position == selectedPosition) {
                        removeElement();
                        arrayListImages.set(position, userModelSingleItemSelect);
                    } else {
                        removeElement();
                        arrayListImages.set(selectedPosition, userModelSingleItemSelect); //here we are highlighting new item

                        arrayListImages.set(position, userModel);               //here we are removing older item from highlighting
                        addDataToSelectedArrayList(userModel, position);
                    }
                } else {
                    arrayListImages.set(position, userModel);
                    addDataToSelectedArrayList(userModel, position);
                }
                notifyDataSetChanged();
                notifyItemChanged(position);
                notifyItemChanged(selectedPosition);

                onItemClickListenerRecycleItems.onRecyclerItemClicked(position, arrayListUserModelSelectedItems);

            }
        }

        private void removeElement() {
            arrayListUserModelSelectedItems.remove(0);
        }

        private void addDataToSelectedArrayList(UserModel userModel, int position) {
            userModel.setImagesPath(arrayListImages.get(position).getImagesPath());
            userModel.setPosition(position);
            arrayListUserModelSelectedItems.add(userModel);
        }
    }

    interface OnItemClickListenerRecycleItems {
        void onRecyclerItemLongClick(int position, ArrayList<UserModel> arrayList);

        void onRecyclerItemClicked(int position, ArrayList<UserModel> arrayList);
    }

    public void setOnItemClickListener(OnItemClickListenerRecycleItems onItemClickListener) {
        onItemClickListenerRecycleItems = onItemClickListener;
    }
}
