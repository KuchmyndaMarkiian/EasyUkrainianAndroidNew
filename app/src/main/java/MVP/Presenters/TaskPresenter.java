package MVP.Presenters;

import Infrastructure.CustomTypes.ParameterPair;
import Infrastructure.CustomTypes.TemplateMethods;
import Infrastructure.Tasks.Sessions.SessionType;
import Infrastructure.Tasks.Sessions.TaskSession;
import Infrastructure.Tasks.Tasks.Task;
import MVP.Views.IView;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import com.example.mark0.easyukrainian.ProfileNewActivity;
import com.example.mark0.easyukrainian.R;
import com.example.mark0.easyukrainian.TaskActivity;

import java.io.Serializable;
import java.util.Map;

import static Infrastructure.Static.EasyUkrApplication.redirectToIntent;
import static Infrastructure.Static.EasyUkrApplication.showToast;

/**
 * Created by MARKAN on 15.05.2017.
 */
public abstract class TaskPresenter implements IPresenter, IRedirectablePresenter {
    TaskSession currentSession = null;
    Object currentOption = null;
    Task currentTask = null;
    int currentIndex = -1;

    IView view;
    Activity activity;

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
        activity = this.view.getCurrentContext();
        initView();
        initEvents();
    }

    protected void initEvents() {
        ImageView next = (ImageView) activity.findViewById(R.id.nextTaskButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextTask();
            }
        });
    }

    protected abstract void initView();

    protected void nextTask() {
        if (currentOption == null) {
            showToast(activity, "Enter answer! Please");
            return;
        }
        currentTask.checkTask(currentOption);
        currentIndex++;
        if (currentIndex >= 9) {
            redirectView(ProfileNewActivity.class, TemplateMethods.formatParameters(
                    new ParameterPair<String, Serializable>("session", currentSession)
            ));
        } else {
            SessionType type=null;
            redirectView(TaskActivity.class, TemplateMethods.formatParameters(
                    new ParameterPair<String, Serializable>("session", currentSession),
                    new ParameterPair<String, Serializable>("type", currentSession.getSessionType()),
                    new ParameterPair<String, Serializable>("index", currentIndex++)
            ));
            activity.finish();
        }
    }

    @Override
    public void redirectView(Class<?> aClass, Map<String, Serializable> extras) {
        redirectToIntent(activity, aClass, false, extras);
    }
}
