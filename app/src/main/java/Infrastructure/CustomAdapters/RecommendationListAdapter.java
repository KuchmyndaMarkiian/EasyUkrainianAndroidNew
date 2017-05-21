package Infrastructure.CustomAdapters;

import Infrastructure.Static.EasyUkrApplication;
import Models.LearningResources.Recommendation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.mark0.easyukrainian.R;

/**
 * Created by Markan on 08.02.2017.
 */
public class RecommendationListAdapter extends CommonAdapter {

    public RecommendationListAdapter(Context context, SparseArray list) {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        GridLayout gl;
        if (convertView == null) {
            final Recommendation element = (Recommendation) elements.get(position);
            gl = new GridLayout(context);
            gl.setColumnCount(2);
            gl.setRowCount(1);
            gl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
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
            gl.addView(img);

            LinearLayout layout;
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            int wrap = ViewGroup.LayoutParams.WRAP_CONTENT;
            TextView t1 = new TextView(context);
            TextView t2 = new TextView(context);
            Button but = new Button(new ContextThemeWrapper(context, R.style.MyButton), null, R.style.MyButton);
            but.setText("OPEN");
            but.setLayoutParams(new LinearLayout.LayoutParams(wrap, wrap));
            but.setGravity(Gravity.CENTER);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(element.getLink())));
                }
            });
            t1.setText(element.getHeader());
            t2.setText(element.getTranslate());
            layout.addView(t1);
            layout.addView(t2);
            layout.addView(but);

            RelativeLayout.LayoutParams linearPar = new RelativeLayout.LayoutParams(wrap, wrap);
            linearPar.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.setLayoutParams(linearPar);
            gl.addView(layout);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                gl.setBackground(context.getDrawable(R.drawable.cornercard));
            }
        } else gl = (GridLayout) convertView;

        return gl;
    }
}
