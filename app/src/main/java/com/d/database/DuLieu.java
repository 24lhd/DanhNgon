package com.d.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import com.d.object.Category;
import com.d.object.DanhNgon;

import java.io.IOException;
import java.util.ArrayList;

import duong.ChucNangPhu;
import duong.sqlite.DuongSQLite;

/**
 * Created by d on 19/01/2017.
 */

public class DuLieu {
    private Context context;
    private DuongSQLite duongSQLite;
    public static final String PATH_DB="/data/data/com.lhd.danhngon/databases/" + "danhngon_db.sqlite";

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
            contentValues.put("author",ChucNangPhu.edata(danhNgon.getAuthor()));
            contentValues.put("content",danhNgon.getContent());
            contentValues.put("category",danhNgon.getCategory());
            contentValues.put("favorite",danhNgon.getCategory());
            openDatabases();
            id=duongSQLite.getDatabase().insert("danhngon", null, contentValues);
           closeDatabases();
        return id;
    }
    public long insertDanhNgonOFF(com.d.object.DanhNgon danhNgon){
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
    public int updateDanhNgonUnfarvorite(DanhNgon danhNgon) {
        int result;
        openDatabases();
        ContentValues values=new ContentValues();
        String [] str={danhNgon.getStt()};
        values.put("stt",danhNgon.getStt());
        values.put("category",danhNgon.getCategory());
        values.put("author",danhNgon.getAuthor());
        values.put("content",danhNgon.getContent());
        values.put("favorite","0");
        result=duongSQLite.getDatabase().update("danhngon",values,"stt=?",str);
        closeDatabases();
        return result;
    }
    public int updateDanhNgonFarvorite(DanhNgon danhNgon) {
        int result;
        openDatabases();
        ContentValues values=new ContentValues();
        String [] str={danhNgon.getStt()};
        values.put("stt",danhNgon.getStt());
        values.put("category",danhNgon.getCategory());
        values.put("author",danhNgon.getAuthor());
        values.put("content",danhNgon.getContent());
        values.put("favorite","1");
        result=duongSQLite.getDatabase().update("danhngon",values,"stt=?",str);
        closeDatabases();
        return result;
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
    public ArrayList<DanhNgon> getDanhNgonFavorites() {
        ArrayList<DanhNgon> danhNgons=new ArrayList<>();
        try {
            String s[]={"1"};
            openDatabases();
            Cursor cursor=duongSQLite.getDatabase().query("danhngon",null,"favorite = ?",s,null,null,null);
//            Cursor cursor=duongSQLite.getDatabase().query("danhngon",null,null,null,null,null,null);
            cursor.moveToFirst();
            int istt=cursor.getColumnIndex("stt");
            int icontent=cursor.getColumnIndex("content");
            int iauthor=cursor.getColumnIndex("author");
            int icategory=cursor.getColumnIndex("category");
            int ifavorite=cursor.getColumnIndex("favorite");
            while (!cursor.isAfterLast()){
                DanhNgon danhNgon=new DanhNgon(
                        cursor.getString(istt),
                       cursor.getString(icontent),
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
    public ArrayList<String> getImages() {
        ArrayList<String> images=new ArrayList<>();
        try {
            openDatabases();
            Cursor cursor=duongSQLite.getDatabase().query("img",null,null,null,null,null,null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int ilink=cursor.getColumnIndex("link");
            while (!cursor.isAfterLast()){
                images.add(cursor.getString(ilink));
                cursor.moveToNext();
            }
            closeDatabases();
            return images ;
        }catch (CursorIndexOutOfBoundsException e){
            ChucNangPhu.showLog("CursorIndexOutOfBoundsException getImages");
            return null;
        }
    }
    public ArrayList<DanhNgon> getDanhNgon() {

        ArrayList<DanhNgon> danhNgons=new ArrayList<>();
        try {
            openDatabases();
//            duongSQLite.getDatabase().delete("data",null,null);
            Cursor cursor=duongSQLite.getDatabase().query("danhngon",null,null,null,null,null,null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int istt=cursor.getColumnIndex("stt");
            int icontent=cursor.getColumnIndex("content");
            int iauthor=cursor.getColumnIndex("author");
            int icategory=cursor.getColumnIndex("category");
            int ifavorite=cursor.getColumnIndex("favorite");
            while (!cursor.isAfterLast()){
                DanhNgon danhNgon=new DanhNgon(
                        cursor.getString(istt),
                        cursor.getString(icontent),
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
    public void deleteDThiLop(String maLop) {
        openDatabases();
        String []s={maLop};
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
