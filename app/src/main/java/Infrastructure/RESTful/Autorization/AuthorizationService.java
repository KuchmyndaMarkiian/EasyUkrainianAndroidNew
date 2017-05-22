package Infrastructure.RESTful.Autorization;

import Hardware.SharedPreferences.UserPreference;
import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.Token;
import Infrastructure.CustomTypes.Tuple;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.OkHttp3Manager;
import Infrastructure.RESTful.WebAPI.WebApiPost;
import Models.AutorizationModels.Abstract.EditingModel;
import Models.AutorizationModels.Abstract.UserModel;
import android.app.Activity;

import static Infrastructure.RESTful.HTTP.OkHttp3Manager.JsonType;
import static Infrastructure.RESTful.HTTP.OkHttp3Manager.WwwFormType;
import static Infrastructure.Static.EasyUkrApplication.showToast;

/**
 * Created by MARKAN on 20.05.2017.
 */
//Авторизаційна система
public class AuthorizationService {
    private final Activity context;
    public boolean isSuccessful = false;
    //Токен доступу до ресурсів
    private Token token;

    public AuthorizationService(Activity context) {
        this.context = context;
    }

    //Логін
    public void login(UserModel model, boolean update) {
        try {
            OkHttp3Manager<Token> manager = new OkHttp3Manager<>(context, ConstURL.getLoginUrl());
            manager.putFormBody(new Tuple<>("grant_type", "password"),
                    new Tuple<>("username", model.getUsername()),
                    new Tuple<>("password", model.getPassword()));
            manager.putHeaders(new Tuple<>("Content-Type", WwwFormType),
                    new Tuple<>("Accept", JsonType));
            manager.execute(OkHttp3Manager.Method.POST, OkHttp3Manager.SynchronizationType.ASYNC);
            if (manager.isSuccessful()) {
                token = manager.getAsObject(Token.class);
                CurrentUser.getInstance().setToken(token);
                if (update) {
                    CurrentUser.updateInfoFromServer(context);
                }
                UserPreference.storeUserAccount();
                isSuccessful = true;

            } else
                throw new Exception(manager.getMessage());
        } catch (final Exception ex) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(context, ex.getMessage());
                }
            });
        }
    }

    //Реєстрація
    public void register(EditingModel model) {
        try {
            WebApiPost apiPost = new WebApiPost();
            try {
                if (apiPost.registerUser(model)) {
                    CurrentUser.updateInfoFromServer(context);
                    isSuccessful = true;
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

    //Вихід із системи
    public void logout() {
        try {
            OkHttp3Manager manager = new OkHttp3Manager(context, ConstURL.getLogOutUrl());
            manager.putHeaders(new Tuple<>(Token.authorizationHeader, CurrentUser.getInstance().getToken().getToken()));
            manager.putRequestBody(null);
            manager.execute(OkHttp3Manager.Method.POST, OkHttp3Manager.SynchronizationType.ASYNC);
            if (manager.isSuccessful()) {
                isSuccessful = true;
            }
        } finally {
            UserPreference.destroyPreference();
        }
    }
}
