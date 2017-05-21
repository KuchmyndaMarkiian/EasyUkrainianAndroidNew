package MVP.Views;

import MVP.Presenters.IPresenter;
import android.app.Activity;

/**
 * Created by mark0 on 24.04.2017.
 */
public interface IView {
    void setPresenter(IPresenter presenter);

    Activity getCurrentContext();
}
