package com.example.facetokimageuploaddemo;

import android.os.Environment;

public class FilePath {

    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String DCIM = ROOT_DIR + "/DCIM";
}
