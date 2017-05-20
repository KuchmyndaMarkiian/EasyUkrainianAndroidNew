package Infrastructure.CustomTypes;

import java.util.HashMap;
import java.util.Map;

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
}
