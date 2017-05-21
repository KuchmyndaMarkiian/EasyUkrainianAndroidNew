package Infrastructure.RESTful.Autorization;

import Hardware.SharedPreferences.UserPreference;
import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.Token;
import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.HttpManager;
import Infrastructure.RESTful.WebAPI.WebApiPost;
import Models.AutorizationModels.Abstract.EditingModel;
import Models.AutorizationModels.Abstract.UserModel;
import android.app.Activity;

import static Infrastructure.RESTful.HTTP.HttpManager.JsonType;
import static Infrastructure.RESTful.HTTP.HttpManager.WwwFormType;
import static Infrastructure.Static.EasyUkrApplication.showToast;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class AutorizationServiceNew {
    private final Activity context;
    public boolean isSuccessful = false;
    Token token;

    public AutorizationServiceNew(Activity context) {
        this.context = context;
    }


    public void login(UserModel model, boolean update) {
        try {
            HttpManager<Token> manager = new HttpManager(context, ConstURL.getLoginUrl());
            manager.putFormBody(new ParameterPair<>("grant_type", "password"),
                    new ParameterPair<>("username", model.getUsername()),
                    new ParameterPair<>("password", model.getPassword()));
            manager.putHeaders(new ParameterPair<>("Content-Type", WwwFormType),
                    new ParameterPair<>("Accept", JsonType));
            manager.execute(HttpManager.Method.POST, HttpManager.SynchronizationType.ASYNC);
            if (manager.isSuccessful()) {
                token = manager.getAsObject(Token.class);
                CurrentUser.getInstance().setToken(token);
                if (update) {
                    CurrentUser.updateInfoFromServer(context);
                }
                UserPreference.storeUserAccount();
                isSuccessful = true;

            } else throw new Exception(manager.getMessage());
        } catch (final Exception ex) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(context, ex.getMessage());
                }
            });
        }
    }

    public void register(EditingModel model) {
        try {
            WebApiPost apiPost = new WebApiPost();
            try {
                if (apiPost.registerUser(model)) {
                    CurrentUser.updateInfoFromServer(context);
                    isSuccessful = true;
                } else {
                }
            } catch (final Exception e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(context, e.getMessage());
                    }
                });
            }
        } catch (final Exception ex) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(context, ex.getMessage());
                }
            });
        }
    }

    public void logout() {
        try {
            HttpManager manager = new HttpManager(context, ConstURL.getLogOutUrl());
            manager.putHeaders(new ParameterPair<>(Token.authorizationHeader, CurrentUser.getInstance().getToken().getToken()));
            manager.putRequestBody(null);
            manager.execute(HttpManager.Method.POST, HttpManager.SynchronizationType.ASYNC);
            if (manager.isSuccessful()) {
                isSuccessful = true;
            }
        } finally {
            CurrentUser.getInstance().clear();
            UserPreference.destroyPreference();
        }
    }
}
