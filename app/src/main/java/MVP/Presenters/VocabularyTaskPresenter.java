package MVP.Presenters;

import Infrastructure.Tasks.Sessions.TaskSession;
import Infrastructure.Tasks.Tasks.GuessTask;
import Infrastructure.Tasks.Tasks.WordTask;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.mark0.easyukrainian.R;

/**
 * Created by MARKAN on 15.05.2017.
 */
public class VocabularyTaskPresenter extends TaskPresenter {


    public VocabularyTaskPresenter(TaskSession session, int index) {
        super(session, index);
    }

    //region Initialize View
    protected void initLayout(LinearLayout layout) {
        initImageView(layout);
        initTextView(layout);
        initRadioGroup(layout);
        initEditor(layout);
    }

    void initImageView(LinearLayout layout) {
        //region ImageView
        if (!(currentTask instanceof WordTask))// Pirture OR Guess
        {
            byte[] image = (byte[]) currentTask.getContent();
            ImageView imageView = new ImageView(activity);
            if (image == null)
                imageView.setImageResource(R.drawable.nope_img);
            else
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            imageView.setMaxWidth(250);
            imageView.setMaxHeight(250);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(15, 15, 15, 15);
            layout.addView(imageView);
        }
        //endregion
    }

    void initTextView(LinearLayout layout) {
        //region TextView
        if (currentTask instanceof WordTask) { // only Word task
            TextView text = new TextView(activity);
            text.setText((String) currentTask.getContent());
            text.setTextSize(48);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setTextColor(ContextCompat.getColor(activity, R.color.blueMain));
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setPadding(15, 15, 15, 15);
            layout.addView(text);
        }
        //endregion
    }

    void initRadioGroup(LinearLayout layout) {
//region RadioGroup
        if (!(currentTask instanceof GuessTask))//Picture or Word
        {
            final RadioGroup group = new RadioGroup(activity);
            String[] strings = (String[]) currentTask.getOptions();
            RadioButton[] radios = new RadioButton[strings.length];
            for (int i = 0; i < radios.length; i++) {
                radios[i] = new RadioButton(new ContextThemeWrapper(activity.getBaseContext(), R.style.MyRadio));
                final RadioButton radio = radios[i];
                radio.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                radio.setText(strings[i]);
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
            layout.addView(group, new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
        //endregion
    }

    void initEditor(LinearLayout layout) {
        //region Editor
        if (currentTask instanceof GuessTask)//only Guess
        {
            EditText editText = new EditText(activity);
            editText.setTextSize(36);
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editText.setTextColor(ContextCompat.getColor(activity, R.color.blueMain));
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            editText.setPadding(15, 15, 15, 35);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Editable a = ((EditText) activity.getCurrentFocus()).getText();
                    if (a == null || a.length() <= 0)
                        currentOption = null;
                    currentOption = a != null ? a.toString() : null;
                }
            });
            layout.addView(editText);
        }
        //endregion
    }

    //endregion
    //region Initialize Events
    @Override
    protected void initEvents() {
        super.initEvents();
        ImageView hint = (ImageView) activity.findViewById(R.id.hintButton);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    //endregion
}
