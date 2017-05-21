package com.example.mark0.easyukrainian;

import Infrastructure.Tasks.Sessions.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TaskChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_choose);
    }

    public void nextToTask(View view) {
        RadioGroup rg = (RadioGroup) findViewById(R.id.taskChoose);
        Intent intent = null;
        TaskSession session = null;
        SessionType sessionType = null;
        switch (rg.indexOfChild(rg.findViewById(rg.getCheckedRadioButtonId()))) {
            case 0:
                intent = new Intent(this, TaskActivity.class);
                session = new VocabularySession();
                sessionType = SessionType.VOCABULARY;
                session.generate(this);
                break;
            case 1:
                intent = new Intent(this, TaskActivity.class);
                session = new GrammarSession();
                sessionType = SessionType.GRAMMAR;
                session.generate(this);
                break;
            case 2:
                intent = new Intent(this, TaskActivity.class);
                session = new ExamSession();
                sessionType = SessionType.EXAM;
                session.generate(this);
                break;
            default:
                Toast.makeText(TaskChooseActivity.this, "Error with taskes", Toast.LENGTH_SHORT).show();

        }
        if (intent != null) {
            intent.putExtra("session", session);
            intent.putExtra("type", sessionType);
            intent.putExtra("index", 0);
            startActivity(intent);
            finish();
        }
    }
}
