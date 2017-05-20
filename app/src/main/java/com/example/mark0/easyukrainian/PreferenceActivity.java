package com.example.mark0.easyukrainian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Window wind = getWindow();
        wind.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        wind.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        wind.setEnterTransition(StaticUI.getAnimation(StaticUI.Animation.SLIDE));
        wind.setExitTransition(StaticUI.getAnimation(StaticUI.Animation.SLIDE));*/
        setContentView(R.layout.activity_preference);
    }

}
