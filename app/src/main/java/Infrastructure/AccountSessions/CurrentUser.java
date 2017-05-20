package Infrastructure.AccountSessions;


import Hardware.WiFiConnector;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.OkHttp;
import Infrastructure.RESTful.WebAPI.WebApiGet;
import Infrastructure.RESTful.WebAPI.WebApiPost;
import Models.SimpleUser;
import android.content.Context;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by mark0 on 26.04.2017.
 */
public class CurrentUser extends User implements Serializable {

    static CurrentUser instance = new CurrentUser();

    private CurrentUser() {
    }

    public static boolean updateInfoFromServer(Context context) {
        WiFiConnector connector = new WiFiConnector(context);
        if (connector.isConnected()) {
            WebApiGet apiGet = new WebApiGet();
            try {
                CurrentUser user = getInstance();
                SimpleUser tmp = apiGet.getUserDetails(user.getToken().getToken());
                user.Copy(tmp);
                user.avatar = apiGet.getFile(user.getToken().getToken(),
                        ConstURL.getAvatarUrl(),
                        OkHttp.ParameterType.HEADER_TYPE, null, null);
            } catch (IOException e) {
                //e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static void updateInfoToServer(Context context) {
        try {
            WiFiConnector connector = new WiFiConnector(context);
            if (connector.isConnected()) {
                WebApiPost apiPost = new WebApiPost();
                apiPost.putDetails(CurrentUser.getInstance().parseToSimpleUser());
            }
        } catch (Exception e) {

        }
    }

    public static CurrentUser getInstance() {
        return instance;
    }

    private void Copy(SimpleUser simpleUser) {
        //CurrentUser user=getInstance();
        setName(simpleUser.getName());
        setNickname(simpleUser.getNickname());
        setSurname(simpleUser.getSurname());
        setEmail(simpleUser.getEmail());
        setLevel(simpleUser.getLevel());
        score=simpleUser.getScore();
        setDateOfBirth(simpleUser.dateOfBirth);
    }
}
