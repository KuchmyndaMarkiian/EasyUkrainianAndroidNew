package Infrastructure.CustomAdapters;

import Models.LearningResources.Dialogue;
import android.content.Context;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import com.example.mark0.easyukrainian.R;

/**
 * Created by MARKAN on 19.05.2017.
 */
public class DialogueAdapter extends CommonAdapter {
    public DialogueAdapter(Context context, SparseArray list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridLayout layout;
        if (convertView == null) {
            Dialogue element = (Dialogue) elements.get(position);
            layout = new GridLayout(context);
            layout.setColumnCount(2);
            layout.setRowCount(1);
            layout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setPadding(6, 6, 6, 6);
            TextView header = new TextView(context);
            header.setText(element.getHeader());
            layout.addView(header);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                layout.setBackground(context.getDrawable(R.drawable.cornercard));
            }
        } else layout = (GridLayout) convertView;

        return layout;
    }
}
