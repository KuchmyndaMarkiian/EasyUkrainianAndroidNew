package Infrastructure.CustomAdapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by MARKAN on 18.05.2017.
 */
public class CommonAdapter extends BaseAdapter {
    protected Context context;
    protected SparseArray elements;

    public CommonAdapter(Context context, SparseArray list) {
        this.context = context;
        elements = list;
    }


    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
