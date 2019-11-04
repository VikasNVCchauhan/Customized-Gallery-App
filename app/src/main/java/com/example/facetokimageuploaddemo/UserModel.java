package com.example.facetokimageuploaddemo;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {

    //    private File imageFile;
    private String directoriesName;
    private String imagesPath;
    private ArrayList<String> arrayListImageCollection;
    private boolean aBoolean;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public void setDirectoriesName(String directoriesName) {
        this.directoriesName = directoriesName;
    }

    public ArrayList<String> getArrayListImageCollection() {
        return arrayListImageCollection;
    }

    public void setArrayListImageCollection(ArrayList<String> arrayListImageCollection) {
        this.arrayListImageCollection = arrayListImageCollection;
    }

    public String getDirectoriesName() {
        return directoriesName;
    }

    public void setSelect(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public boolean isSelect() {
        return aBoolean;
    }
}
