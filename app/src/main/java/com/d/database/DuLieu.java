package com.d.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import com.d.object.Category;
import com.d.object.DanhNgon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import duong.ChucNangPhu;
import duong.sqlite.DuongSQLite;

/**
 * Created by d on 19/01/2017.
 */

public class DuLieu {
    private Context context;
    private DuongSQLite duongSQLite;
    public static final String PATH_DB="/data/data/com.d.danhngon/databases/" + "danhngon_db.sqlite";

    public DuongSQLite getDuongSQLite() {
        return duongSQLite;
    }

    public DuLieu(Context context) {
        this.context=context;
        duongSQLite=new DuongSQLite();
    }

    public boolean checkDB() {
        return duongSQLite.checkDataBase(PATH_DB);
    }

    public long insertDanhNgon(com.d.object.DanhNgon danhNgon){
        long id = 0;
            ContentValues contentValues=new ContentValues();
            contentValues.put("author",danhNgon.getAuthor());
            contentValues.put("content",danhNgon.getContent());
            contentValues.put("category",danhNgon.getCategory());
            contentValues.put("favorite",danhNgon.getCategory());
            openDatabases();
            id=duongSQLite.getDatabase().insert("danhngon", null, contentValues);
           closeDatabases();
        return id;
    }
    public ArrayList<Category> getCategory() {
        ArrayList<Category> categories=new ArrayList<>();
        try {
            openDatabases();
            Cursor cursor=duongSQLite.getDatabase().query("category",null,null,null,null,null,null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int icategory=cursor.getColumnIndex("category");
            int ma=cursor.getColumnIndex("ma");
            while (!cursor.isAfterLast()){
                Category category=new Category(cursor.getString(icategory),cursor.getString(ma));
                categories.add(category);
                cursor.moveToNext();
            }
            closeDatabases();
            return categories ;
        }catch (CursorIndexOutOfBoundsException e){
            return null;
        }
    }
    public ArrayList<DanhNgon> getDanhNgon() {
        ArrayList<DanhNgon> danhNgons=new ArrayList<>();
        try {
            openDatabases();
            Cursor cursor=duongSQLite.getDatabase().query("danhngon",null,null,null,null,null,null);
            cursor.getCount();// tra ve so luong ban ghi no ghi dc
            cursor.getColumnNames();// 1 mang cac cot
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int icontent=cursor.getColumnIndex("content");
            int iauthor=cursor.getColumnIndex("author");
            int icategory=cursor.getColumnIndex("category");
            int ifavorite=cursor.getColumnIndex("favorite");
            while (!cursor.isAfterLast()){
                DanhNgon danhNgon=new DanhNgon(cursor.getString(icontent),
                        cursor.getString(iauthor),
                        cursor.getString(icategory),
                        cursor.getString(ifavorite));
                danhNgons.add(danhNgon);
                cursor.moveToNext();
            }
            closeDatabases();
            return danhNgons ;
        }catch (CursorIndexOutOfBoundsException e){
            ChucNangPhu.showLog("CursorIndexOutOfBoundsException");
            return null;
        }
    }
    public String decode(String str, String str2) {
        int i = 0;
        if (str.indexOf(str2) < 0) {
            return str;
        }
        String[] split = str.split(str2);
        String stringBuffer = new StringBuffer(split[1] + split[0]).reverse().toString();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (i < stringBuffer.length()) {
            byteArrayOutputStream.write(Integer.parseInt(stringBuffer.substring(i, i + 2), 16));
            i += 2;
        }
        return new String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF-8"));
    }
    public void deleteDThiLop(String maLop) {
        openDatabases();
        String []s={maLop};
//        database.delete("dthilop","maLop=?",s);
        closeDatabases();
    }
    private void closeDatabases() {
        duongSQLite.cloneDatabase();
    }

    private void openDatabases() {
        try {
            duongSQLite.copyDataBase(context,PATH_DB,"danhngon_db.sqlite");
            if (duongSQLite.checkDataBase(PATH_DB))
                duongSQLite.createOrOpenDataBases(context,"danhngon_db");
        } catch (IOException e) {
            Log.e("faker","openDatabases");
        }

    }

}
