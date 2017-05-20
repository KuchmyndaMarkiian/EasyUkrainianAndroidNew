package MVP.Presenters;

import Hardware.SharedPreferences.UserPreference;
import Hardware.WiFiConnector;
import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.User;
import Infrastructure.RESTful.Autorization.AutorizationService;
import Infrastructure.RESTful.WebAPI.WebApiGet;
import MVP.Views.IView;
import Models.AutorizationModels.Abstract.UserModel;
import com.example.mark0.easyukrainian.ProfileNewActivity;

import java.io.IOException;

import static Infrastructure.Static.EasyUkrApplication.showToast;


/**
 * Created by mark0 on 26.04.2017.
 */
public class LoginPresenter extends AutorizationPresenter {

    public LoginPresenter() {
        model = new UserModel();
    }

    public void login() {
        if (checkModel()) {
            String message = null;
            Boolean result = false;
            if ((new WiFiConnector(view.getCurrentContext().getBaseContext()).isConnected())) {
                AutorizationService service = new AutorizationService(activity);
                service.loginModel(model);
                result = service.isSuccessfull();
                if (result) {
                    WebApiGet apiGet = new WebApiGet();
                    try {
                        User user=CurrentUser.getInstance();
                        user.cloneFrom(apiGet.getUserDetails(CurrentUser.getInstance().getToken().getToken()));
                        UserPreference.storeUserAccount(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                message = service.getAutorizationMessage();
            } else {
                message = "Wifi isn`t connected";
            }
            showToast(activity, (result ? "Successful! " : "Error! ") + message);
            if (result) {
                redirectView(ProfileNewActivity.class, null);
            }
        }
    }

    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
        activity = view.getCurrentContext();
    }


}
