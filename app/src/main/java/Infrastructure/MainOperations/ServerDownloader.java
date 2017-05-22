package Infrastructure.MainOperations;

import Infrastructure.CustomTypes.Tuple;
import Infrastructure.RESTful.HTTP.OkHttp3Manager;
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
    //Стягування файлу з серверу
    public static byte[] getFile(Context context, String url, OkHttp3Manager.ParameterType parameterType, String type, String imageId) {
        return getFile(context, null, url, parameterType, type, imageId);
    }

    public static byte[] getFile(Context context, String token, String url, OkHttp3Manager.ParameterType parameterType, String type, String imageId) {
        OkHttp3Manager okHttp3Manager = new OkHttp3Manager(context, url);
        if (parameterType == OkHttp3Manager.ParameterType.HEADER) {
            okHttp3Manager.putHeaders(new Tuple<>("authorization", token),
                    new Tuple<>("type", type),
                    new Tuple<>("id", imageId));
        } else {
            okHttp3Manager.putParameters(new Tuple<>("authorization", token),
                    new Tuple<>("type", type),
                    new Tuple<>("id", imageId));
        }
        okHttp3Manager.execute(OkHttp3Manager.Method.GET, OkHttp3Manager.SynchronizationType.SYNC);
        if (okHttp3Manager.isSuccessful()) {
            return okHttp3Manager.getAsBytes();
        }
        return null;
    }

    //endregion
    //Перетворення JSON ресурсів у об'єкти
    public DeserializedType getContent(String url, Type type) {
        OkHttp3Manager<DeserializedType> manager = new OkHttp3Manager<>(contex, url);
        manager.execute(OkHttp3Manager.Method.GET, OkHttp3Manager.SynchronizationType.ASYNC);
        if (manager.isSuccessful()) {
            return manager.getAsObject(type);
        }
        return null;
    }
}