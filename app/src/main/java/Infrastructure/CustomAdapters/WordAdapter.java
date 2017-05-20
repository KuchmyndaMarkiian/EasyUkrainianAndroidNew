package Infrastructure.CustomAdapters;

import Infrastructure.Static.EasyUkrApplication;
import Models.LearningResources.Word;
import android.content.Context;
import android.os.Build;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mark0.easyukrainian.R;

/**
 * Created by Markan on 08.02.2017.
 */
public class WordAdapter extends CommonAdapter {

    public WordAdapter(Context context, SparseArray list) {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            Word element = (Word) elements.get(position);
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
            gridParams.setGravity(Gravity.CENTER);
            gridParams.setMargins(12, 12, 12, 12);
            layout.setLayoutParams(gridParams);
            layout.setPadding(6, 6, 6, 6);
            ImageView img = new ImageView(context);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(360, 360);
            img.setLayoutParams(params);
            byte[] image = element.getImage();
            if (image == null)
                img.setImageResource(R.drawable.nope_img);
            else {
                img.setImageBitmap(EasyUkrApplication.getBitmap(image));
            }
            layout.addView(img);
            TextView t1 = new TextView(context);
            TextView t2 = new TextView(context);
            t1.setText(element.getWord());
            t2.setText(element.getTranslate());
            layout.addView(t1);
            layout.addView(t2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                layout.setBackground(context.getDrawable(R.drawable.cornercard));
            }
        } else layout = (LinearLayout) convertView;

        return layout;
    }
}
