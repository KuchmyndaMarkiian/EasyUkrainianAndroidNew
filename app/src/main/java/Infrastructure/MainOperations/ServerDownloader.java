package Infrastructure.MainOperations;

import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.RESTful.HTTP.HttpManager;
import android.content.Context;

import java.lang.reflect.Type;

/**
 * Created by MARKAN on 21.05.2017.
 */
public class ServerDownloader<DeserializedType> {
    private final Context contex;

    public ServerDownloader(Context context) {
        this.contex = context;
    }

    //region Static
    public static byte[] getFile(String url, String type, String imageId) {
        return getFile(null, null, url, HttpManager.ParameterType.PARAMETER, type, imageId);
    }

    public static byte[] getFile(Context context, String token, String url, HttpManager.ParameterType parameterType, String type, String imageId) {
        HttpManager httpManager = new HttpManager(context, url);
        if (parameterType == HttpManager.ParameterType.HEADER) {
            httpManager.putHeaders(new ParameterPair<>("authorization", token),
                    new ParameterPair<>("type", type),
                    new ParameterPair<>("id", imageId));
        } else {
            httpManager.putParameters(new ParameterPair<>("authorization", token),
                    new ParameterPair<>("type", type),
                    new ParameterPair<>("id", imageId));
        }
        httpManager.execute(HttpManager.Method.GET, HttpManager.SynchronizationType.SYNC);
        if (httpManager.isSuccessful()) {
            return httpManager.getAsBytes();
        }
        return null;
    }

    public DeserializedType getContent(String url, Type type) {
        HttpManager<DeserializedType> manager = new HttpManager<>(contex, url);
        manager.execute(HttpManager.Method.GET, HttpManager.SynchronizationType.ASYNC);
        if (manager.isSuccessful()) {
            return manager.getAsObject(type);
        }
        return null;
    }
    //endregion
}