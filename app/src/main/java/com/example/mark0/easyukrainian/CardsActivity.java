package com.example.mark0.easyukrainian;

import Hardware.Storage.EasyUkrFiles;
import MVP.Presenters.CardsPresenter;
import MVP.Presenters.IPresenter;
import MVP.Views.IView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class CardsActivity extends AppCompatActivity implements IView {
    CardsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        setPresenter(new CardsPresenter());

        Intent _this = getIntent();
        EasyUkrFiles.Type type = (EasyUkrFiles.Type) _this.getSerializableExtra("type");
        presenter.setType(type);
        presenter.init();
    }
    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = (CardsPresenter) presenter;
        this.presenter.setView(this);
        this.presenter.setGridView((GridView) findViewById(R.id.gridList));
    }

    @Override
    public Activity getCurrentContext() {
        return this;
    }
}


