package Infrastructure.Static;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.mark0.easyukrainian.R;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Markan on 18.03.2017.
 */
public class EasyUkrApplication {

    public static Bitmap getBitmap(byte[] bytes) {
        return bytes != null ? BitmapFactory.decodeByteArray(bytes, 0, bytes.length) : null;
    }

    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static Dialog initDialog(Activity context) {
        Dialog res = new Dialog(context);
        res.setContentView(R.layout.activity_custom_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(res.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        res.getWindow().setAttributes(lp);
        return res;
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
