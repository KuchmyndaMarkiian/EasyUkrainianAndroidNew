package MVP.Presenters;

import MVP.Views.IView;

/**
 * Created by mark0 on 24.04.2017.
 */
public interface IPresenter {
    IView getView();

    void setView(IView view);
}
