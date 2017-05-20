package Infrastructure.CustomTypes;

import java.util.*;

/**
 * Created by MARKAN on 15.05.2017.
 */
public final class TemplateMethods {
    @SafeVarargs
    public static <F, S> Map<F, S> formatParameters(ParameterPair<F, S>... params) {
        Map<F, S> map = new HashMap<>();
        for (ParameterPair<F, S> param : params) {
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
        Collection result = filter;
        return (T) result;
    }

    public static <T extends Collection> void swap(T object1, T object2) {
        Object[] obj1 = object1.toArray();
        Object[] obj2 = object2.toArray();
        object1 = (T) Arrays.asList(obj2);
        object2 = (T) Arrays.asList(obj1);
    }

    public static <T> void swap(T object1, T object2) {
        T tmp = object1;
        object1 = object2;
        object2 = tmp;
    }
}
