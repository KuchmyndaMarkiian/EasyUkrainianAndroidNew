package Infrastructure.RESTful.HTTP;

import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.Token;
import Infrastructure.CustomTypes.TemplateMethods;
import Infrastructure.CustomTypes.Tuple;
import Infrastructure.RESTful.ConstURL;
import Models.AutorizationModels.Abstract.UserModel;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import com.google.gson.Gson;
import okhttp3.*;

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

    public static boolean requestPostWithImage(String url, String filepath, Tuple<String, String> parameter, String mediaType) throws IOException {
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
    //region User Opreations
    public static boolean requestToken(String url, UserModel model, String mediaType) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Map<String, String> params = TemplateMethods.formatParameters(
                new Tuple<>("Content-Type", mediaType),
                new Tuple<>("Accept", JsonType));
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.addHeader(param.getKey(), param.getValue());
        }
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        params = TemplateMethods.formatParameters(
                new Tuple<>("grant_type", "password"),
                new Tuple<>("username", model.getUsername()),
                new Tuple<>("password", model.getPassword()));
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
    //endregion
}
