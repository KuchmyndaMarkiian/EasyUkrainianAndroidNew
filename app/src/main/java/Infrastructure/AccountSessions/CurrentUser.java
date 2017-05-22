package Infrastructure.AccountSessions;


import Hardware.WiFiConnector;
import Infrastructure.CustomTypes.Tuple;
import Infrastructure.MainOperations.JsonConverter;
import Infrastructure.MainOperations.ServerDownloader;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.OkHttp3Manager;
import Models.SimpleUser;
import android.app.Activity;
import android.content.Context;

import java.io.Serializable;

import static Infrastructure.RESTful.HTTP.OkHttp3Manager.Method.GET;
import static Infrastructure.RESTful.HTTP.OkHttp3Manager.Method.POST;
import static Infrastructure.RESTful.HTTP.OkHttp3Manager.SynchronizationType.ASYNC;
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
                        ConstURL.getAvatarUrl(), OkHttp3Manager.ParameterType.HEADER, null, null);
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
                OkHttp3Manager manager = new OkHttp3Manager(context, ConstURL.getUpdateUserInfo());
                manager.putHeaders(new Tuple<>(Token.authorizationHeader, CurrentUser.getInstance().getToken().getToken()));
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
            }
        } catch (Exception ignored) {

        }
    }

    private static SimpleUser getInfoFromServer(Context context) {
        OkHttp3Manager<SimpleUser> manager = new OkHttp3Manager<>(context, ConstURL.getUserUrl());
        manager.putHeaders(new Tuple<>(Token.authorizationHeader, getInstance().token.getToken()));
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
        maxScore = simpleUser.getMaxScore();
        score = simpleUser.getScore();
        setDateOfBirth(simpleUser.dateOfBirth);
    }
}
