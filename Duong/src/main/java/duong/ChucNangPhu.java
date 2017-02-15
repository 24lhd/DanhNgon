package duong;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by D on 15/01/2017.
 */

public class ChucNangPhu {
    /**
     * Phương thức tải file .apk từ 1 url về máy và cài đặt khi đã tải xong
     * @param activity đối tượng gọi cần 1 Activity
     * @param appName Tên file .apk được tải về
     * @param urlApp link url chứa file apk
     * @param content nội dung của trạng thái update
     * @param title       title cua thong báo
     */
    public void downloadAndInstallAppLication(final Activity activity, String appName, String urlApp, String content, String title) {
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        final String fileName = appName;
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);
        //Delete update file if exists
        File file = new File(destination);
        if (file.exists())
            //file.delete() - test this, I think sometimes it doesnt work
            file.delete();
        //get url of app on server
        String url = urlApp;
        //set downloadmanager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(content);
        request.setTitle(title);
        //set destination
        request.setDestinationUri(uri);
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);
        //set BroadcastReceiver to install app when .apk is downloaded
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + fileName)), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(install);
                activity.unregisterReceiver(this);
                activity.finish();
            }
        };
        //register receiver for when .apk download is compete
        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void sendEmail(String mail,String title,String content,Context context) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mail});
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT   , content);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "Bạn chưa cài ứng dụng Emails.", Toast.LENGTH_SHORT).show();
        }
    }
    private static boolean exit=false;
    public static void finishDoubleCick(Activity context) {
        if (exit==false){
            Toast.makeText(context,"Nhấn back 1 phát nữa để thoát. ",Toast.LENGTH_SHORT).show();
        }
        exit=!exit;
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                exit=!exit;
            }
        },500);
        if (!exit){
            context.finish();
        }
    }
    public static void showLog(String log) {
        Log.e("faker",log);
    }
    public static String edata(String str) {
        String en="";
        char [] arrstr= str.toCharArray();
        for (int i = 0; i < str.length(); i++) en=en+Integer.toHexString((int) arrstr[i])+"eo";
        return en;
    }
    public static String ddata(String str) {
        String de="";
        String [] arrstr= str.split("eo");
        for (int i = 0; i < arrstr.length; i++) de=de+(char) Integer.parseInt(arrstr[i], 16);
        return de;
    }
}
