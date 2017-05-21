package com.example.mark0.easyukrainian;

import Hardware.Storage.EasyUkrFiles;
import Infrastructure.RESTful.Autorization.AutorizationServiceNew;
import Infrastructure.Tasks.Sessions.ITaskSession;
import MVP.Presenters.IPresenter;
import MVP.Presenters.ProfilePresenter;
import MVP.Views.IProfileView;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProfileNewActivity extends AppCompatActivity
        implements IProfileView {
    private ProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setPresenter(new ProfilePresenter((ITaskSession) getIntent().getSerializableExtra("session")));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class<?> aClass = null;
        Map<String, Serializable> extras = new HashMap<>();
        switch (item.getItemId()) {
            case R.id.nav_setting: {
                aClass = PreferenceActivity.class;//intent = new Intent(this, PreferenceActivity.class);
                break;
            }
            case R.id.nav_vocabulary: {
                aClass = CardsActivity.class;
                extras.put("type", EasyUkrFiles.Type.TOPIC);
                break;
            }
            case R.id.nav_grammar: {
                aClass = CardsActivity.class;
                extras.put("type", EasyUkrFiles.Type.GRAMMAR);
                break;
            }
            case R.id.nav_game: {
                aClass = GameActivity.class;
                break;
            }
            case R.id.nav_task: {
                aClass = TaskChooseActivity.class;
                break;
            }
            case R.id.nav_dialogue: {
                aClass = CardsActivity.class;
                extras.put("type", EasyUkrFiles.Type.DIALOGUE);
                break;
            }
            case R.id.nav_recommend: {
                aClass = CardsActivity.class;
                extras.put("type", EasyUkrFiles.Type.RECOMENDATION);
                break;
            }
            case R.id.logOut: {
                AutorizationServiceNew service = new AutorizationServiceNew(this);
                service.logout();

                if (service.isSuccessful) {
                    aClass = LoginActivity.class;
                }
                break;
            }
        }
        if (aClass != null)
            presenter.redirectView(aClass, extras);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (aClass == LoginActivity.class)
            finish();
        return true;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = (ProfilePresenter) presenter;
        this.presenter.setView(this);
    }

    @Override
    public Activity getCurrentContext() {
        return this;
    }
}
