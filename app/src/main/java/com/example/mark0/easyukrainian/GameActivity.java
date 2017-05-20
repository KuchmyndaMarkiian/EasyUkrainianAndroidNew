package com.example.mark0.easyukrainian;

import MVP.Presenters.GamePresenter;
import MVP.Presenters.IPresenter;
import MVP.Views.IView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements IView {
    private GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setPresenter(new GamePresenter());
        presenter.init();
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = (GamePresenter) presenter;
        this.presenter.setView(this);
    }

    @Override
    public Activity getCurrentContext() {
        return this;
    }
}
