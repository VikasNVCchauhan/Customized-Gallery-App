package com.example.facetokimageuploaddemo;

import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SearchFile {

    private static UserModel userModel;
    private static ArrayList<String> arrayListDirectoriesName = new ArrayList<>();
    private static ArrayList<String> arrayListDirectoriesPath = new ArrayList<>();
    private static ArrayList<UserModel> arrayListInitializedImages = new ArrayList<>();

    public static ArrayList<String> getDirectoriesPath(String directory) {
        File file = new File(directory);
        File[] listFile = file.listFiles();
        for (int i = 0; i < listFile.length; i++) {
            if (listFile[i].isDirectory()) {
                if (listFile[i].length() != 0) {
                    if (!arrayListDirectoriesName.contains(listFile[i].getName())) {                 //To avoid duplicate directories in array list
                        arrayListDirectoriesPath.add(listFile[i].getAbsolutePath());
                        arrayListDirectoriesName.add(listFile[i].getName());
                    }
                }
            }
        }
        return arrayListDirectoriesPath;
    }

    public static ArrayList<String> getDirectoriesName() {
        return arrayListDirectoriesName;
    }

    public static ArrayList<UserModel> getDirectoriesImages(String directoryPath, String directoryName) {

        File file = new File(directoryPath);
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (arrayListInitializedImages.size() < 400) {
                //userModel = new UserModel(listFiles[i], directoryName);
                arrayListInitializedImages.add(userModel);
            } else {
                break;
            }
        }
        return arrayListInitializedImages;
    }
}
