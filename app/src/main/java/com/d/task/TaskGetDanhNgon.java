package com.d.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.d.database.DuLieu;
import com.d.object.DanhNgon;

import java.util.ArrayList;

/**
 * Created by d on 19/01/2017.
 */

public class TaskGetDanhNgon extends AsyncTask<Void, Void, ArrayList<DanhNgon>> {
    private Context context;
    private Handler handler;
    public TaskGetDanhNgon(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected ArrayList<DanhNgon>doInBackground(Void... params) {
        DuLieu duLieu=new DuLieu(context);
        return duLieu.getDanhNgon();
    }
    @Override
    protected void onPostExecute(ArrayList<DanhNgon> danhNgons) {
        Message message=new Message();
        message.obj=danhNgons;
        handler.sendMessage(message);
    }
}
