package Infrastructure.RESTful.HTTP;

import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.MainOperations.JsonConverter;
import android.content.Context;
import android.os.StrictMode;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Infrastructure.CustomTypes.TemplateMethods.formatParameters;
import static Infrastructure.RESTful.HTTP.HttpManager.Method.GET;
import static Infrastructure.RESTful.HTTP.HttpManager.Method.POST;
import static Infrastructure.Static.EasyUkrApplication.showToast;
import static com.example.mark0.easyukrainian.UserRegisterActivity.path;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class HttpManager<Type> {
    public static String JsonType = "application/json; charset=utf-8";
    public static String WwwFormType = "application/x-www-form-urlencoded";
    public static String Multipart = "multipart/mixed";

    static {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    String mainUrl;
    Response result;
    Context context;
    //endregion
    //region Exception & Message code
    String message = "Cool";
    private OkHttpClient client;
    private SynchronizationType synchronizationType;
    private Request.Builder headerBuilder;
    private HttpUrl.Builder parameterBuilder;
    private FormBody.Builder bodyBuilder;
    private RequestBody requestBody;
    private MultipartBody.Builder multiBuilder;

    public HttpManager(Context context, String url) {
        this.context = context;
        this.mainUrl = url;
        client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();

        headerBuilder = (new Request.Builder()).url(url);
        parameterBuilder = null;
        bodyBuilder = null;
    }

    //region Main Methods
    private Response get() {
        try {
            if (headerBuilder == null)
                headerBuilder = new Request.Builder();
            if (bodyBuilder != null) {

            }
            if (parameterBuilder != null) {
                return execute(headerBuilder.url(parameterBuilder.build()).build());
            }
            if (bodyBuilder != null) {
                return execute(headerBuilder.post(bodyBuilder.build()).build());
            }
            return execute(headerBuilder.url(mainUrl).build());
        } catch (Exception ex) {

            message = ex.getMessage();
            return null;
        }
    }

    private Response post() {
        try {
            if (headerBuilder != null) {
                headerBuilder.url(mainUrl);
            }
            if (bodyBuilder != null) {

            }
            if (parameterBuilder != null) {

            }
            if (requestBody != null) {
                return execute(headerBuilder.post(requestBody).build());
            }
            if (headerBuilder != null && bodyBuilder != null) {
                return execute(headerBuilder.post(bodyBuilder.build()).build());
            }
        } catch (Exception ex) {
            message = ex.getMessage();

        }
        return null;
    }

    //endregion
    //region Executing Code
    public void execute(Method method, SynchronizationType type) {
        synchronizationType = type;
        try {
            if (method == POST) {
                result = post();
            } else if (method == GET) {
                result = get();
            }
            generateMessage(result);

        } catch (IOException e) {
            e.printStackTrace();
            showToast(context, e.getMessage());
        }
    }

    Response execute(Request request) {
        Response result;
        int times = 0;
        do {
            try {
                if (synchronizationType == SynchronizationType.SYNC)
                    result = executeSync(request);
                else
                    result = executeAsync(request);

            } catch (IOException e) {
                e.printStackTrace();
                showToast(context, e.getMessage());
                result = null;
            }
            times++;
        }
        while (times <= 1000 && result == null);
        return result;
    }

    Response executeSync(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    Response executeAsync(Request request) throws IOException {
        final Response[] responseResult = new Response[1];
        final boolean[] isFinished = {false};
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                isFinished[0] = true;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseResult[0] = response;
                isFinished[0] = true;
            }
        });
        while (!isFinished[0]) ;
        return responseResult[0];
    }

    //endregion
    //region Returning results
    public boolean isSuccessful() {
        return result.isSuccessful();
    }

    public String getAsString() {
        try {
            return result.body().string();
        } catch (IOException e) {
            showToast(context, message);
            return null;
        }
    }

    public byte[] getAsBytes() {
        try {
            return result.body().bytes();
        } catch (IOException e) {
            showToast(context, message);
            return null;
        }
    }

    public Type getAsObject(java.lang.reflect.Type type) {
        return (Type) JsonConverter.deserialize(getAsString(), type);
    }

    //endregion
    //region Headers and Parameters
    public void putHeaders(ParameterPair... data) {
        headerBuilder = new Request.Builder();
        putHeaderFromCollection(formatParameters(data));
    }

    public void putParameters(ParameterPair... data) {
        parameterBuilder = HttpUrl.parse(mainUrl).newBuilder();
        putParameterFromCollection(formatParameters(data));
    }

    public void putFormBody(ParameterPair... data) {
        bodyBuilder = new FormBody.Builder();
        putFormBodyFromCollection(formatParameters(data));
    }

    public void putRequestBody(String jsonObject) {
        if (jsonObject == null)
            requestBody = RequestBody.create(null, new byte[0]);
        else
            requestBody = RequestBody.create(MediaType.parse(JsonType), jsonObject);
    }

    public void putMultipartBody(String filepath) {
        multiBuilder = new MultipartBody.Builder();
        File file = new File(filepath);
        String[] splited = path.split("/");
        String filename = splited[splited.length - 1];
        multiBuilder.addFormDataPart("avatar", filename
                , RequestBody.create(MediaType.parse(Multipart), file))
                .build();
    }

    void putHeaderFromCollection(Map<String, String> map) {
        for (Map.Entry<String, String> param : map.entrySet()) {
            headerBuilder.addHeader(param.getKey(), param.getValue());
        }
    }

    void putParameterFromCollection(Map<String, String> map) {
        for (Map.Entry<String, String> param : map.entrySet()) {
            parameterBuilder.addQueryParameter(param.getKey(), param.getValue());
        }
    }

    void putFormBodyFromCollection(Map<String, String> map) {
        for (Map.Entry<String, String> param : map.entrySet()) {
            bodyBuilder.add(param.getKey(), param.getValue());
        }
    }

    public String getMessage() {
        return message;
    }

    void generateMessage(Response response) throws IOException {
        if (!response.isSuccessful()) {
            message = response.body().string();
            showToast(context, message);
        }
    }

    public enum ParameterType {HEADER, PARAMETER}

    public enum Method {GET, POST}

    public enum SynchronizationType {ASYNC, SYNC}
    //endregion
}
