package com.example.mark0.easyukrainian;

import MVP.Presenters.IPresenter;
import MVP.Presenters.RegisterPresenter;
import MVP.Views.IView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.mvc.imagepicker.ImagePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;

import static Infrastructure.Static.EasyUkrApplication.getRealPathFromUri;


public class UserRegisterActivity extends AppCompatActivity implements IView,DatePickerDialog.OnDateSetListener {

    private static final int REQUEST = 1;
    public static String path;
    ImageView img;
    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        setPresenter(new RegisterPresenter());

        img = (ImageView) findViewById(R.id.avatar);
        ImagePicker.setMinQuality(600, 600);
    }

    public void pickAvatar(View view) {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        Bitmap imgage = null;

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri path1 = imageReturnedIntent.getData();
            try {
                imgage = MediaStore.Images.Media.getBitmap(getContentResolver(), path1);
                path = getRealPathFromUri(this, path1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img.setImageBitmap(imgage);
        }
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }

    public void datePick(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog pickerDialog = DatePickerDialog.newInstance(
                UserRegisterActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        pickerDialog.setAccentColor(getResources().getColor(R.color.blueMain, null));
        pickerDialog.show(getFragmentManager(), "Choose date of birth");
    }

    public void registerAcc(View view) {
        EditText edit = (EditText) findViewById(R.id.login);
        presenter.setUsername(edit.getText().toString());
        edit = (EditText) findViewById(R.id.password);
        presenter.setPassword(edit.getText().toString());
        edit = (EditText) findViewById(R.id.confPassword);
        presenter.setConfirmPassword(edit.getText().toString());
        edit = (EditText) findViewById(R.id.mail);
        presenter.setEmail(edit.getText().toString());
        edit = (EditText) findViewById(R.id.name);
        presenter.setName(edit.getText().toString());
        edit = (EditText) findViewById(R.id.surname);
        presenter.setSurname(edit.getText().toString());
        presenter.register();
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = (RegisterPresenter) presenter;
        this.presenter.setView(this);
    }

    @Override
    public Activity getCurrentContext() {
        return this;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        presenter.setDateOfBirth(dayOfMonth, monthOfYear + 1, year);
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        ((Button) findViewById(R.id.dateBirth)).setText(date);
    }
}

