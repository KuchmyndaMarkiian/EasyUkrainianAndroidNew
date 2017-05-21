package com.example.mark0.easyukrainian;

import MVP.Presenters.IPresenter;
import MVP.Presenters.LoginPresenter;
import MVP.Views.IView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements IView {

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPresenter(new LoginPresenter());
    }

    public void login(View view) {
        EditText editText = (EditText) findViewById(R.id.login);
        presenter.setUsername(editText.getEditableText().toString());
        editText = (EditText) findViewById(R.id.password);
        presenter.setPassword(editText.getEditableText().toString());
        presenter.login();
    }

    public void registerView(View view) {
        presenter.redirectView(UserRegisterActivity.class, null);
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = (LoginPresenter) presenter;
        this.presenter.setView(this);
    }

    @Override
    public Activity getCurrentContext() {
        return this;
    }
}
