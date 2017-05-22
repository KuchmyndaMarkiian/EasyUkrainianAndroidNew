package Infrastructure.Patterns.Builders;

import Infrastructure.CustomAdapters.GameAdapter;
import MVP.Presenters.GamePresenter;
import MVP.Presenters.IPresenter;
import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.example.mark0.easyukrainian.R;

/**
 * Created by MARKAN on 18.05.2017.
 */
//Будівельник для генерації компонентів гри та подій на кнопках
public class AnagramAdapterBuilder implements IBuilder {
    private IPresenter presenter;
    public AnagramAdapterBuilder(IPresenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public void setParts() {
        final GamePresenter gamePresenter = (GamePresenter) presenter;
        GridView viewGame = gamePresenter.getGridLayout();
        Activity descrPart = gamePresenter.getView().getCurrentContext();
        final TextView typedText = (TextView) descrPart.findViewById(R.id.typedWord);
        SparseArray<Character> characters = gamePresenter.getCollection();
        viewGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence sequence = typedText.getText();
                String text = sequence.toString();
                Button button = (Button) view;
                text += button.getText();
                button.setEnabled(false);
                gamePresenter.addClickedButton(button);
                typedText.setText(text);
            }
        });
        viewGame.setAdapter(
                new GameAdapter(gamePresenter.getView().getCurrentContext().getBaseContext(),
                        characters));
    }
}