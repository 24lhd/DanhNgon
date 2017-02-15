package com.d.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.d.database.DuLieu;

import java.util.ArrayList;

/**
 * Created by D on 15/02/2017.
 */

public class TaskGetImage extends AsyncTask<Void, Void, ArrayList<String>> {
private Context context;
private Handler handler;
public TaskGetImage(Context context,Handler handler){
        this.context=context;
        this.handler=handler;
        }

    @Override
    protected ArrayList<String>doInBackground(Void...params){
                DuLieu duLieu=new DuLieu(context);
            if(duLieu.getImages()!=null){
                return duLieu.getImages();
            }
            return null;
        }
    @Override
    protected void onPostExecute(ArrayList<String>danhNgons){
            Message message=new Message();
            message.obj=danhNgons;
            handler.sendMessage(message);
        }
}
