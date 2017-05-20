package com.example.mark0.easyukrainian;

import Hardware.Storage.EasyUkrFiles;
import Infrastructure.Serialization.Deserializer;
import Infrastructure.Static.EasyUkrApplication;
import Models.LearningResources.Dialogue;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class DialogueDatailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_datails);

        Integer id = (Integer) (getIntent()).getSerializableExtra("dialogue");
        if (id != null) {
            ArrayList<Dialogue> dialogues = (new Deserializer<Dialogue>(EasyUkrFiles.Type.DIALOGUE, getExternalFilesDir(null))).readObject();
            for (Dialogue dialogue : dialogues) {
                if (Objects.equals(dialogue.getId(), id)) {
                    ((TextView) findViewById(R.id.dialogueHeader)).setText(dialogue.getHeader());
                    ((ImageView) findViewById(R.id.dialogueImage)).setImageBitmap(
                            EasyUkrApplication.getBitmap(dialogue.getImage()));
                    ((TextView) findViewById(R.id.dialogueEng)).setText(dialogue.getDialogueEng());
                    ((TextView) findViewById(R.id.dialogueUkr)).setText(dialogue.getDialogueUkr());
                }
            }
        }
    }
}
