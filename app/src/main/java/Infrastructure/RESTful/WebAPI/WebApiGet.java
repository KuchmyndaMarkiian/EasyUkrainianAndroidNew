package Infrastructure.RESTful.WebAPI;

import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.RESTful.HTTP.OkHttp;
import Models.SimpleUser;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import static Infrastructure.CustomTypes.TemplateMethods.formatParameters;
import static Infrastructure.RESTful.ConstURL.getUserUrl;

/**
 * Created by mark0 on 24.04.2017.
 */
public class WebApiGet {

    private String getResponseBody(String token, String url) throws IOException {
        return OkHttp.requestGetAsString(url,
                formatParameters(
                        new ParameterPair<>("authorization", token)
                ));
    }

    public byte[] getFile(String token, String url, OkHttp.ParameterType paramType, String type, String imageId) throws IOException {
        return OkHttp.requestGetAsBytes(url, paramType, formatParameters(
                new ParameterPair<>("authorization", token),
                new ParameterPair<>("type", type),
                new ParameterPair<>("id", imageId)
        ));
    }
    public SimpleUser getUserDetails(String token) throws IOException {
        return (new Gson()).fromJson(getResponseBody(token, getUserUrl()), SimpleUser.class);
    }

    public Object getContent(String url,Type type)throws IOException {
        return (new Gson()).fromJson(getResponseBody(null, url), type);
    }
}
