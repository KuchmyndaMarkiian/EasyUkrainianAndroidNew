package Infrastructure.RESTful.WebAPI;

import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.CustomTypes.Tuple;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.OkHttp;
import Models.AutorizationModels.Abstract.EditingModel;
import com.google.gson.Gson;

import java.io.IOException;

import static Infrastructure.RESTful.HTTP.OkHttp.*;
import static com.example.mark0.easyukrainian.UserRegisterActivity.path;


/**
 * Created by mark0 on 24.04.2017.
 */


public class WebApiPost {
    public Boolean registerUser(EditingModel model) throws IOException {
        return requestPost(ConstURL.getRegisterUrl(), (new Gson()).toJson(model), OkHttp.JsonType, null)
                && requestToken(ConstURL.getLoginUrl(), model, WwwFormType)
                && requestPostWithImage(ConstURL.getUploadUrl(), path,
                new Tuple<>("authorization", CurrentUser.getInstance().getToken().getToken())
                , Multipart);
    }
}
