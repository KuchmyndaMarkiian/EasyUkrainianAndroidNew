package MVP.Presenters;

import Infrastructure.Tasks.Sessions.TaskSession;
import Infrastructure.Tasks.Tasks.GuessTask;
import android.support.v4.content.res.ResourcesCompat;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.mark0.easyukrainian.R;

import java.util.ArrayList;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class GrammarTaskPresenter extends TaskPresenter implements IRedirectablePresenter {

    public GrammarTaskPresenter(TaskSession session, int index) {
        currentSession = session;
        currentIndex = index;
        currentTask = session.getTask(currentIndex);
    }

    @Override
    protected void initView() {
        activity = view.getCurrentContext();
        TextView description = (TextView) activity.findViewById(R.id.description);
        description.setText(currentTask.getSummary());
        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.block);
        initLayout(layout);
    }

    void initLayout(LinearLayout layout) {
        if (currentTask == null)
            return;
        //Header
        layout.addView(initTextView(currentTask.getSummary()));
        //Description
        layout.addView(initTextView((String) currentTask.getContent()));
        //Group
        layout.addView(initRadioGroup(),new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    TextView initTextView(String text) {
        TextView view = new TextView(activity);
        view.setText(text);
        view.setPadding(6, 6, 6, 6);
        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    RadioGroup initRadioGroup() {
        RadioGroup group = new RadioGroup(activity);
        ArrayList<String> strings = (ArrayList<String>) currentTask.getOptions();
        RadioButton[] radios = new RadioButton[strings.size()];
        for (int i = 0; i < radios.length; i++) {
            radios[i] = new RadioButton(new ContextThemeWrapper(activity.getBaseContext(), R.style.MyRadio));
            final RadioButton radio = radios[i];
            radio.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radio.setText(strings.get(i));
            radio.setChecked(false);
            radio.setPadding(15, 15, 15, 35);
            radio.setTextSize(16);
            radio.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            radio.setTextColor(ResourcesCompat.getColor(activity.getResources(), R.color.textStock, null));

            final int finalI = i;
            radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    currentOption = finalI;
                }
            });
            group.addView(radio);
        }
        group.setBackground(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.radiogroup, null));

        return group;
    }
}
