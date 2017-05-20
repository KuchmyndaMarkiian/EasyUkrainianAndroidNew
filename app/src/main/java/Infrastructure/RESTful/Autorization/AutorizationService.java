package Infrastructure.RESTful.Autorization;

import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.User;
import Infrastructure.RESTful.WebAPI.WebApiPost;
import Infrastructure.Static.EasyUkrApplication;
import Models.AutorizationModels.Abstract.EditingModel;
import Models.AutorizationModels.Abstract.UserModel;
import android.app.Activity;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by mark0 on 26.04.2017.
 */
public class AutorizationService {
    String autorizationMessage;
    Status status;
    Activity context;
    public AutorizationService(Activity context) {
        this.context = context;
        status = Status.NONE;
    }

    public void registerModel(EditingModel model) {
        WebApiPost apiPost = new WebApiPost();
        try {
            if (apiPost.registerUser(model)) {
                CurrentUser.updateInfoFromServer(context.getBaseContext());
                status = Status.REGISTERED;
                autorizationMessage = "Your account is created. Enjoy Ukrainian";
            } else {
                status = Status.WRONG_DATA;
                autorizationMessage = "Maybe this user exists or wrond data";
            }
        } catch (IOException e) {
            status = Status.FAULTY;
            autorizationMessage = EasyUkrApplication.getFullExceptionMessage(e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void logOut(User user) {
        WebApiPost apiPost = new WebApiPost();
        try {
            if (apiPost.logOut(user)) {
                status = Status.LOGGEDOUT;
                autorizationMessage = "You`re logged out";
            } else {
                status = Status.FAULTY;
                autorizationMessage = "Server error";
            }
        } catch (IOException e) {
            status = Status.FAULTY;
            autorizationMessage = EasyUkrApplication.getFullExceptionMessage(e);
        }
    }

    public void loginModel(UserModel model) {
        WebApiPost apiPost = new WebApiPost();
        try {
            if (apiPost.loginUser(model)) {
                CurrentUser.updateInfoFromServer(context.getBaseContext());
                status = Status.SIGNED_IN;
                autorizationMessage = "You`re signed.";
            } else {
                status = Status.WRONG_DATA;
                autorizationMessage = "Maybe wrong data";
            }
        } catch (IOException e) {
            status = Status.FAULTY;
            autorizationMessage = EasyUkrApplication.getFullExceptionMessage(e);
        }
    }

    public Boolean isSuccessfull() {
        return status == Status.REGISTERED || status == Status.SIGNED_IN || status == Status.LOGGEDOUT;
    }

    public String getAutorizationMessage() {
        return autorizationMessage;
    }

    enum Status {
        SIGNED_IN,
        REGISTERED,
        NONE,
        FAULTY,
        WRONG_DATA,
        LOGGEDOUT
    }
}
