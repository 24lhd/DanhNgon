package com.d.database;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import duong.sqlite.DuongSQLite;

/**
 * Created by d on 19/01/2017.
 */

public class DuLieu {
    private DuongSQLite duongSQLite;

    public DuLieu(Context context) {
        duongSQLite=new DuongSQLite();
        try {
            duongSQLite.copyDataBase(context,"back.sqlite");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("faker",""+duongSQLite.checkDataBase("/data/data/"+context.getPackageName()+"/databases/" + "back.sqlite"));
    }

}
