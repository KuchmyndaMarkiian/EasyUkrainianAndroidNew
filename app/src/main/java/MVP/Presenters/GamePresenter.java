package MVP.Presenters;

import Infrastructure.CustomTypes.Tuple;
import Infrastructure.Patterns.Builders.AnagramAdapterBuilder;
import Infrastructure.Patterns.Builders.IBuilder;
import Infrastructure.Static.EasyUkrApplication;
import Infrastructure.Tasks.Sessions.GameSession;
import MVP.Views.IView;
import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.example.mark0.easyukrainian.ProfileNewActivity;
import com.example.mark0.easyukrainian.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import static Infrastructure.CustomTypes.TemplateMethods.formatParameters;
import static Infrastructure.Static.EasyUkrApplication.redirectToIntent;

/**
 * Created by MARKAN on 18.05.2017.
 */
//Пред'явник для гри
public class GamePresenter implements IPresenter, IRedirectablePresenter {
    private IView view;
    private GameSession gameSession;
    private GridView gridLayout;
    private ArrayList<Button> buttons;
    private ArrayList<Button> clickedButtons;
    public GamePresenter() {
        buttons = new ArrayList<>();
        clickedButtons = new ArrayList<>();
    }

    //Ініціалізація поля
    public void init() {
        final Activity contex = view.getCurrentContext();
        gridLayout =
                (GridView) contex.findViewById(R.id.anagramfield);
        gameSession.generate(view.getCurrentContext());
        IBuilder builder = new AnagramAdapterBuilder(this);
        builder.setParts();
        //region init button events
        contex.findViewById(R.id.checkButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String word = ((TextView) contex.findViewById(R.id.typedWord)).getText().toString();
                        if (gameSession.checkAnagram(word)) {
                            if (gameSession.isFinished()) {
                                redirectView(ProfileNewActivity.class, formatParameters(new Tuple<String, Serializable>("session", gameSession)));
                            }
                            TextView txtv = (TextView) contex.findViewById(R.id.foundedWords);
                            String text = txtv.getText().toString();
                            if (!(text.isEmpty() || text.equals("")))
                                text.concat("; ");
                            txtv.setText(text.concat(word).concat("; "));
                            buttons.addAll(clickedButtons);
                            resetClickedButtons();
                        } else {
                            EasyUkrApplication.showToast(getView().getCurrentContext(), "Wrong combination");
                        }
                    }
                });
        contex.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) contex.findViewById(R.id.typedWord)).setText("");
                resetClickedButtonsForce();
            }
        });
        //endregion
    }
    @Override
    public IView getView() {
        return view;
    }
    @Override
    public void setView(IView view) {
        this.view = view;
        gameSession = new GameSession(this.view.getCurrentContext());
    }
    public GridView getGridLayout() {
        return gridLayout;
    }

    //region Алгоритм для роботи з кнопками
    public void addClickedButton(Button button) {
        clickedButtons.add(button);
    }
    private void resetClickedButtonsForce() {
        for (Button button : clickedButtons) {
            button.setEnabled(true);
        }
        clickedButtons.clear();
    }
    private void resetClickedButtons() {
        clickedButtons.clear();
    }

    //endregion
    public SparseArray<Character> getCollection() {
        ArrayList<Character> tmp = gameSession.getGenerategData();
        int index = 0;
        SparseArray result = new SparseArray<Character>();
        for (Character character : tmp) {
            result.put(index++, character);
        }
        return result;
    }
    @Override
    public void redirectView(Class<?> aClass, Map<String, Serializable> extras) {
        redirectToIntent(getView().getCurrentContext(), aClass, true, extras);
    }
}
