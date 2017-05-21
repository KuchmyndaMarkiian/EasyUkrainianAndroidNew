package Infrastructure.RESTful.HTTP;

import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.Token;
import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.CustomTypes.TemplateMethods;
import Infrastructure.RESTful.ConstURL;
import Models.AutorizationModels.Abstract.UserModel;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.example.mark0.easyukrainian.UserRegisterActivity.path;

/**
 * Created by mark0 on 26.04.2017.
 */
@SuppressLint("NewApi")
public final class OkHttp {
    public static String JsonType = "application/json; charset=utf-8";
    public static String WwwFormType = "application/x-www-form-urlencoded";
    public static String Multipart = "multipart/mixed";
    private static OkHttpClient client = new OkHttpClient();

    static {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private static Response execute(Request request) throws IOException {
        Response response;
        while (true) {
            response = client.newCall(request).execute();
            if (response.code() == 200)
                return response;
        }

    }

    //region Type of Parameters
    private static Request requestGetWithHeaders(String url, Map<String, String> parameters) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, String> item : parameters.entrySet()) {
                builder.addHeader(item.getKey(), item.getValue());
            }
        }
        return builder.build();
    }

    private static Request requestGetWithParameters(String url, Map<String, String> parameters) {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, String> item : parameters.entrySet()) {
                builder.addQueryParameter(item.getKey(), item.getValue());
            }
        }
        return new Request.Builder().url(builder.build()).build();
    }

    //endregion
    //region POST
    public static boolean requestPost(String url, String json, String mediaType, Map<String, String> headers) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), json);
        Request request;
        if (headers == null) {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        } else {
            Request.Builder requestBuilder = new Request.Builder();
            for (Map.Entry<String, String> param : headers.entrySet()) {
                requestBuilder.addHeader(param.getKey(), param.getValue());
            }
            request = requestBuilder.build();
        }

        return execute(request).code() == 200;
    }

    public static boolean requestPostWithImage(String url, String filepath, ParameterPair<String, String> parameter, String mediaType) throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        File file = new File(filepath);
        String[] splited = path.split("/");
        String filename = splited[splited.length - 1];
        builder.addFormDataPart("avatar", filename
                , RequestBody.create(MediaType.parse(mediaType), file))
                .build();
        Request request = new Request.Builder().url(url).header(parameter.key, parameter.value).post(builder.build()).build();
        return execute(request).code() == 200;
    }

    //endregion
    //region GET
    public static String requestGetAsString(String url, Map<String, String> parameters) throws IOException {
        return execute(requestGetWithHeaders(url, parameters)).body().string();
    }

    public static byte[] requestGetAsBytes(String url, ParameterType parameterType, Map<String, String> parameters) throws IOException {
        switch (parameterType) {
            case HEADER_TYPE:
                return IOUtils.toByteArray(execute(requestGetWithHeaders(url, parameters)).body().byteStream());
            case PARAMETER_TYPE:
                return IOUtils.toByteArray(execute(requestGetWithParameters(url, parameters)).body().byteStream());
            default:
                return null;
        }
    }

    //endregion
    //region User Opreations
    public static boolean requestToken(String url, UserModel model, String mediaType) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Map<String, String> params = TemplateMethods.formatParameters(
                new ParameterPair<>("Content-Type", mediaType),
                new ParameterPair<>("Accept", JsonType));
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.addHeader(param.getKey(), param.getValue());
        }
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        params = TemplateMethods.formatParameters(
                new ParameterPair<>("grant_type", "password"),
                new ParameterPair<>("username", model.getUsername()),
                new ParameterPair<>("password", model.getPassword()));
        for (Map.Entry<String, String> param : params.entrySet()) {
            bodyBuilder.add(param.getKey(), param.getValue());
        }
        Request request = builder.post(bodyBuilder.build()).build();
        Response response = execute(request);
        if (response.code() == 200) {
            CurrentUser.getInstance().setToken((new Gson()).fromJson(response.body().string(), Token.class));
            return true;
        }
        return false;
    }

    public static boolean logOut(String token) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(ConstURL.getLogOutUrl());
        builder.addHeader("authorization", token);
        return execute(builder
                .method("POST",
                        RequestBody.create(null, new byte[0]))
                .build()).code() == 200;
    }

    public enum ParameterType {HEADER_TYPE, PARAMETER_TYPE}
    //endregion
}
