package Infrastructure.CustomAdapters;

import Models.LearningResources.Grammar;
import android.content.Context;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import com.example.mark0.easyukrainian.R;

/**
 * Created by Markan on 08.02.2017.
 */
public class GrammarAdapter extends CommonAdapter {


    public GrammarAdapter(Context context, SparseArray list) {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GridLayout layout;
        if (convertView == null) {
            Grammar element = (Grammar) elements.get(position);
            layout = new GridLayout(context);
            layout.setColumnCount(2);
            layout.setRowCount(1);
            layout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setPadding(6, 6, 6, 6);
            TextView header = new TextView(context);
            header.setText(element.getHeader().concat("\n").concat(element.getTranslate()));
            layout.addView(header);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                layout.setBackground(context.getDrawable(R.drawable.cornercard));
            }
        } else layout = (GridLayout) convertView;

        return layout;
    }
}
