package Infrastructure.RESTful.HTTP;

import Infrastructure.CustomTypes.Tuple;
import Infrastructure.MainOperations.JsonConverter;
import android.content.Context;
import android.os.StrictMode;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Infrastructure.CustomTypes.TemplateMethods.formatParameters;
import static Infrastructure.RESTful.HTTP.OkHttp3Manager.Method.GET;
import static Infrastructure.RESTful.HTTP.OkHttp3Manager.Method.POST;
import static Infrastructure.Static.EasyUkrApplication.showToast;
import static com.example.mark0.easyukrainian.UserRegisterActivity.path;

/**
 * Created by MARKAN on 20.05.2017.
 *
 * Алас для роботи з HTTP протоколом (передача/прийом даних з певними властивостями)
 */
public class OkHttp3Manager<Type> {
    public static String JsonType = "application/json; charset=utf-8";
    public static String WwwFormType = "application/x-www-form-urlencoded";
    public static String Multipart = "multipart/mixed";

    static {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private String mainUrl;
    private Response result;
    private Context context;

    private String message = "Cool";
    private OkHttpClient client;
    private SynchronizationType synchronizationType;
    private Request.Builder headerBuilder;
    private HttpUrl.Builder parameterBuilder;
    private FormBody.Builder bodyBuilder;
    private RequestBody requestBody;
    private MultipartBody.Builder multiBuilder;

    public OkHttp3Manager(Context context, String url) {
        this.context = context;
        this.mainUrl = url;

        //Встановлення часу з'єднування та перебування на зв'язку
        client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();

        headerBuilder = (new Request.Builder()).url(url);
        parameterBuilder = null;
        bodyBuilder = null;
    }

    //region Main Methods

    //GET метод
    private Response get() {
        try {
            if (headerBuilder == null)
                headerBuilder = new Request.Builder();
            if (parameterBuilder != null) {
                return execute(headerBuilder.url(parameterBuilder.build()).build());
            }
            if (bodyBuilder != null) {
                return execute(headerBuilder.post(bodyBuilder.build()).build());
            }
            return execute(headerBuilder.url(mainUrl).build());
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        return null;
    }

    //POST метод
    private Response post() {
        try {
            if (headerBuilder != null) {
                headerBuilder.url(mainUrl);
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
    //Основний код для виклику ф-ії GET/POST методу ти типу
    // синхронізації(синхронний та асинхронний виклик)
    // іншими об'єктами
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

    //Ф-ія що запускає роботу методу GET/POST
    private Response execute(Request request) {
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

    //Синхронний виклик
    private Response executeSync(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    //Асинхронний виклик
    private Response executeAsync(Request request) {
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
    //Чи успішна операція?
    public boolean isSuccessful() {
        return result.isSuccessful();
    }

    //Повернути як стрічку JSON
    public String getAsString() {
        try {
            return result.body().string();
        } catch (IOException e) {
            showToast(context, message);
            return null;
        }
    }

    //Повернути як потік байтів
    public byte[] getAsBytes() {
        try {
            return result.body().bytes();
        } catch (IOException e) {
            showToast(context, message);
            return null;
        }
    }

    //Повернути як вказаний об'єкт
    public Type getAsObject(java.lang.reflect.Type type) {
        return (Type) JsonConverter.deserialize(getAsString(), type);
    }

    //endregion
    //region Headers and Parameters
    // (Налаштування параметрів передачі)
    public void putHeaders(Tuple... data) {
        headerBuilder = new Request.Builder();
        putHeaderFromCollection(formatParameters(data));
    }

    public void putParameters(Tuple... data) {
        parameterBuilder = HttpUrl.parse(mainUrl).newBuilder();
        putParameterFromCollection(formatParameters(data));
    }

    public void putFormBody(Tuple... data) {
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

    private void putHeaderFromCollection(Map<String, String> map) {
        for (Map.Entry<String, String> param : map.entrySet()) {
            headerBuilder.addHeader(param.getKey(), param.getValue());
        }
    }

    private void putParameterFromCollection(Map<String, String> map) {
        for (Map.Entry<String, String> param : map.entrySet()) {
            parameterBuilder.addQueryParameter(param.getKey(), param.getValue());
        }
    }

    private void putFormBodyFromCollection(Map<String, String> map) {
        for (Map.Entry<String, String> param : map.entrySet()) {
            bodyBuilder.add(param.getKey(), param.getValue());
        }
    }

    //endregion
    //region Exception & Message code
    public String getMessage() {
        return message;
    }

    private void generateMessage(Response response) throws IOException {
        if (!response.isSuccessful()) {
            message = response.body().string();
            showToast(context, message);
        }
    }

    //endregion
    public enum ParameterType {
        HEADER, PARAMETER
    }

    public enum Method {GET, POST}

    public enum SynchronizationType {ASYNC, SYNC}
}
