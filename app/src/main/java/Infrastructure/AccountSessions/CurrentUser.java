package Infrastructure.AccountSessions;


import Hardware.WiFiConnector;
import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.MainOperations.JsonConverter;
import Infrastructure.MainOperations.ServerDownloader;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.HttpManager;
import Models.SimpleUser;
import android.app.Activity;
import android.content.Context;

import java.io.Serializable;

import static Infrastructure.RESTful.HTTP.HttpManager.Method.GET;
import static Infrastructure.RESTful.HTTP.HttpManager.Method.POST;
import static Infrastructure.RESTful.HTTP.HttpManager.SynchronizationType.ASYNC;
import static Infrastructure.Static.EasyUkrApplication.showToast;

/**
 * Created by mark0 on 26.04.2017.
 */
public class CurrentUser extends User implements Serializable {

    private static CurrentUser instance = new CurrentUser();

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        return instance;
    }

    public static boolean updateInfoFromServer(Context context) {
        WiFiConnector connector = new WiFiConnector(context);
        if (connector.isConnected()) {
            try {
                CurrentUser user = getInstance();
                SimpleUser tmp = getInfoFromServer(context);
                user.Copy(tmp);
                user.avatar = ServerDownloader.getFile(context, getInstance().getToken().getToken(),
                        ConstURL.getAvatarUrl(), HttpManager.ParameterType.HEADER, null, null);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static void updateInfoToServer(final Activity context) {
        try {
            WiFiConnector connector = new WiFiConnector(context.getBaseContext());
            if (connector.isConnected()) {
                HttpManager manager = new HttpManager(context, ConstURL.getUpdateUserInfo());
                manager.putHeaders(new ParameterPair<>(Token.authorizationHeader, CurrentUser.getInstance().getToken().getToken()));
                manager.putRequestBody(JsonConverter.serialize(CurrentUser.getInstance().parseToSimpleUser()));
                manager.execute(POST, ASYNC);
                if (manager.isSuccessful()) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(context, "Updated");
                        }
                    });
                }


                /*WebApiPost apiPost = new WebApiPost();
                apiPost.putDetails(CurrentUser.getInstance().parseToSimpleUser());*/
            }
        } catch (Exception e) {

        }
    }

    private static SimpleUser getInfoFromServer(Context context) {
        HttpManager<SimpleUser> manager = new HttpManager<>(context, ConstURL.getUserUrl());
        manager.putHeaders(new ParameterPair<>(Token.authorizationHeader, getInstance().token.getToken()));
        manager.execute(GET, ASYNC);
        if (manager.isSuccessful()) {
            return manager.getAsObject(SimpleUser.class);
        }
        return null;
    }

    private void Copy(SimpleUser simpleUser) {
        setName(simpleUser.getName());
        setNickname(simpleUser.getNickname());
        setSurname(simpleUser.getSurname());
        setEmail(simpleUser.getEmail());
        setLevel(simpleUser.getLevel());
        score = simpleUser.getScore();
        setDateOfBirth(simpleUser.dateOfBirth);
    }

    public void clear() {
        CurrentUser user = this;
        user = null;
    }
}
