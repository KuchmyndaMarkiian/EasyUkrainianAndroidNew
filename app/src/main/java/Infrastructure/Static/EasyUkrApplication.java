package Infrastructure.Static;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by Markan on 18.03.2017.
 */
public class EasyUkrApplication {

    public static Context Context;

    public static void killThread(Thread thread)
    {
        if(thread!=null)
        {
            thread.interrupt();
        }
    }
    public static void killThread(Thread... threads)
    {
        for (Thread thread:threads) {
            killThread(thread);
        }
    }

    public static byte[] getImageBytes(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static byte[] getImageBytes(Bitmap imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageView.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmap(Drawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmap(byte[] bytes) {
        return bytes != null ? BitmapFactory.decodeByteArray(bytes, 0, bytes.length) : null;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getFullExceptionMessage(Exception e) {
        StringBuilder builder = new StringBuilder();
        builder.append("Main message: ");
        builder.append(e.getMessage()).append("\n");
        builder.append("Caused by:");
        try {
            builder.append(e.getCause().getMessage());
        } finally {
            return builder.toString();
        }
    }

    public static void redirectToIntent(Activity activity, Class<?> aClass, boolean finish, Map<String, Serializable> extras) {
        Intent intent = new Intent(activity, aClass);
        if (extras != null) {
            for (Map.Entry<String, Serializable> extra : extras.entrySet()) {
                intent.putExtra(extra.getKey(), extra.getValue());
            }
        }
        activity.startActivity(intent);
        if (finish)
            activity.finish();
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
