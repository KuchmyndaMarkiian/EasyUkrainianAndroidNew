package Infrastructure.MainOperations;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class JsonConverter {
    static Gson gson = new Gson();

    public static Object deserialize(String s, Type type) {
        return gson.fromJson(s, type);
    }

    public static String serialize(Object model) {
        return gson.toJson(model);
    }
}
