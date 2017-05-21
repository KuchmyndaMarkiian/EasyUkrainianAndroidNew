package MVP.Presenters;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by mark0 on 26.04.2017.
 */
public interface IRedirectablePresenter {
    void redirectView(Class<?> aClass, Map<String, Serializable> extras);
}