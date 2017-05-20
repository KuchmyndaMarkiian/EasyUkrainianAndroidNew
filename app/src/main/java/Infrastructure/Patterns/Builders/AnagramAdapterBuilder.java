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

import java.util.ArrayList;

/**
 * Created by MARKAN on 18.05.2017.
 */
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
        TextView foundedWords = (TextView) descrPart.findViewById(R.id.foundedWords);
        SparseArray<Character> characters = gamePresenter.getCollection();
        final ArrayList<Button> buttons = gamePresenter.getButtons();

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