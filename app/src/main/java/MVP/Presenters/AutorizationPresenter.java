package MVP.Presenters;

import Infrastructure.Interfaces.IAuthorization;
import Infrastructure.Interfaces.IModelChecking;
import MVP.Views.IView;
import Models.AutorizationModels.Abstract.UserModel;
import android.app.Activity;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import static Infrastructure.Static.EasyUkrApplication.redirectToIntent;

/**
 * Created by mark0 on 26.04.2017.
 */
public class AutorizationPresenter implements IPresenter, IModelChecking, IAuthorization, IRedirectablePresenter {
    IView view;
    UserModel model;
    Activity activity;

    public AutorizationPresenter() {
        model = new UserModel();
    }

    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean checkModel() {
        try {
            if (Objects.equals(model.getUsername(), ""))
                throw new Exception("Nickname is empty");
            if (Objects.equals(model.getPassword(), ""))
                throw new Exception("Password is empty");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void setUsername(String name) {
        model.setUsername(name);
    }

    @Override
    public void setPassword(String password) {
        model.setPassword(password);
    }


    @Override
    public void redirectView(Class<?> aClass, Map<String, Serializable> extras) {
        redirectToIntent(activity, aClass, true, extras);
    }
}
