package com.example.mark0.easyukrainian;

import Infrastructure.Tasks.Sessions.SessionType;
import Infrastructure.Tasks.Sessions.TaskSession;
import MVP.Presenters.*;
import MVP.Views.IView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity implements IView {

    TaskPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent current = getIntent();
        IPresenter gettedPresenter = null;
        switch ((SessionType) current.getSerializableExtra("type")) {
            case VOCABULARY:
                gettedPresenter = new VocabularyTaskPresenter((TaskSession) current.getSerializableExtra("session"),
                        current.getIntExtra("index", -1));
                break;
            case GRAMMAR:
                gettedPresenter = new GrammarTaskPresenter((TaskSession) current.getSerializableExtra("session"),
                        current.getIntExtra("index", -1));
                break;
            case EXAM:
                gettedPresenter = new ExamTaskPresenter((TaskSession) current.getSerializableExtra("session"),
                        current.getIntExtra("index", -1));
                break;
        }

        setPresenter(gettedPresenter);
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = (TaskPresenter) presenter;
        this.presenter.setView(this);
    }

    @Override
    public Activity getCurrentContext() {
        return this;
    }
}
