package Infrastructure.CustomTypes;

import java.util.*;

/**
 * Created by MARKAN on 15.05.2017.
 */
public final class TemplateMethods {
    @SafeVarargs
    public static <F, S> Map<F, S> formatParameters(Tuple<F, S>... params) {
        Map<F, S> map = new HashMap<>();
        for (Tuple<F, S> param : params) {
            if (param.value != null)
                map.put(param.key, param.value);
        }
        return map;
    }

    public static <T extends Collection> T shakeCollection(T elements) {
        ArrayList filter = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        Object[] objects = elements.toArray();
        int length = objects.length;
        while (filter.size() < length) {
            Object o = objects[random.nextInt(length)];
            if (!filter.contains(o))
                filter.add(o);
        }
        return (T) filter;
    }
}
