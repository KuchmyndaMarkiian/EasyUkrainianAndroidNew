package Infrastructure.CustomAdapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by MARKAN on 18.05.2017.
 */
//Розширений адаптер для генерації подій на кнопках та зовнішнього вигляду
public class GameAdapter extends CommonAdapter {
    public GameAdapter(Context context, SparseArray list) {
        super(context, list);
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        Button layout;
        if (convertView == null) {
            Character element = (Character) elements.get(position);
            layout = new Button(context);
            layout.setText(Character.toString(element));
            final int pos = position;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, pos, 0);
                }
            });
        } else layout = (Button) convertView;
        return layout;
    }
}
