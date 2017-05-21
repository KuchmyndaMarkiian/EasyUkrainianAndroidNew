package com.example.mark0.easyukrainian;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.mvc.imagepicker.ImagePicker;

import java.util.Objects;

public class UserEditActivity extends AppCompatActivity {

    ImageView img;
    EditText login;
    EditText mail;
    EditText password;
    ImageView avatar;

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        img = (ImageView) findViewById(R.id.avatar);
        ImagePicker.setMinQuality(600, 600);
        //dateButton=(Button) findViewById(R.id.dateBirth);
        login = (EditText) findViewById(R.id.login);
        mail = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.password);
        avatar = (ImageView) findViewById(R.id.avatar);
    }

    public void pickAvatar(View view) {
        ImagePicker.pickImage(this, "Select your avatar:");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        img.setImageBitmap(ImagePicker.getImageFromResult(this, requestCode, resultCode, imageReturnedIntent));
    }

    public void save(View view) {
        try {
            String log = login.getText().toString();
            String pass = password.getText().toString();
            String mail1 = mail.getText().toString();
            if (Objects.equals(log, ""))
                throw new Exception("Login is empty");
            if (!isValidEmail(mail1))
                throw new Exception("E-Mail is empty or incorrect");
            if (!password.isEnabled() && Objects.equals(pass, ""))
                throw new Exception("Password is empty");
        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage(ex.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
        } finally {
            startActivity(new Intent(this, ProfileNewActivity.class));
        }
    }
}

