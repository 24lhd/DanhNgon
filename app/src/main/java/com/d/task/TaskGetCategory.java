package com.d.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.d.database.DuLieu;
import com.d.object.Category;

import java.util.ArrayList;

/**
 * Created by d on 19/01/2017.
 */

public class TaskGetCategory extends AsyncTask<Void, Void, ArrayList<Category>> {
    private Context context;
    private Handler handler;
    public TaskGetCategory(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
    @Override
    protected ArrayList<Category> doInBackground(Void... params) {
        DuLieu duLieu=new DuLieu(context);
        return duLieu.getCategory();
    }
    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        Message message=new Message();
        message.obj=categories;
        handler.sendMessage(message);
    }
}
