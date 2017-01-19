package duong;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by D on 15/01/2017.
 */

public class ScreenShort {
    public static File takeSreenShortByView(View viewInput, Context context) {
        Date now = new Date();
        ByteArrayOutputStream bytearrayoutputstream;
        Bitmap bitmap;
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File file;
        bytearrayoutputstream = new ByteArrayOutputStream();
        FileOutputStream fileoutputstream;
        View view=viewInput.getRootView();
        view.setDrawingCacheEnabled(true);
        bitmap = view.getDrawingCache();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
        file = new File( Environment.getExternalStorageDirectory() + "/GaCongNghiep/"+now+".png");
        file.getParentFile().mkdirs();
        try{
            file.createNewFile();
            fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(bytearrayoutputstream.toByteArray());
            fileoutputstream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context,""+file.getPath(),Toast.LENGTH_SHORT).show();
        return file;
    }

    public static void shareImageByFile(File file,Context context){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            context.startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Không tìm thấy ứng dụng để mở file", Toast.LENGTH_SHORT).show();
        }
    }
    public static void shareText(Context context, String sms ,String title) {
        Toast.makeText(context,sms,Toast.LENGTH_SHORT).show();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sms);
        context.startActivity(Intent.createChooser(sharingIntent, title));
    }
}
