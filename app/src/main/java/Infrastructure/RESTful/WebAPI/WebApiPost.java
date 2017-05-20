package Infrastructure.RESTful.WebAPI;

import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.User;
import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.OkHttp;
import Models.AutorizationModels.Abstract.EditingModel;
import Models.AutorizationModels.Abstract.UserModel;
import Models.SimpleUser;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;

import static Infrastructure.CustomTypes.TemplateMethods.formatParameters;
import static Infrastructure.RESTful.HTTP.OkHttp.*;
import static com.example.mark0.easyukrainian.UserRegisterActivity.path;


/**
 * Created by mark0 on 24.04.2017.
 */


public class WebApiPost {

    public Boolean registerUser(EditingModel model) throws IOException, URISyntaxException {
        return requestPost(ConstURL.getRegisterUrl(), (new Gson()).toJson(model), OkHttp.JsonType, null)
                && requestToken(ConstURL.getLoginUrl(), model, WwwFormType)
                && requestPostWithImage(ConstURL.getUploadUrl(), path,
                new ParameterPair<>("authorization", CurrentUser.getInstance().getToken().getToken())
                , Multipart);
    }

    public Boolean loginUser(UserModel model) throws IOException {
        return requestToken(ConstURL.getLoginUrl(), model, OkHttp.WwwFormType);
    }

    public boolean logOut(User user) throws IOException {
        return OkHttp.logOut(user.getToken().getToken());
    }

    public boolean putDetails(SimpleUser model) throws Exception {
        ParameterPair<String, String> param = new ParameterPair<>("authorization", CurrentUser.getInstance().getToken().getToken());
        return requestPost(ConstURL.getUpdateUserInfo(), (new Gson()).toJson(model), JsonType, formatParameters(param))
                && (path == null || OkHttp.requestPostWithImage(ConstURL.getUploadUrl(), path, param, Multipart));
    }
}
