package MVP.Presenters;

import Infrastructure.CustomTypes.TemplateMethods;
import Infrastructure.CustomTypes.Tuple;
import Infrastructure.Tasks.Sessions.TaskSession;
import Infrastructure.Tasks.Tasks.Task;
import MVP.Views.IView;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    static Object currentOption = null;
    TaskSession currentSession = null;
    Task currentTask = null;
    int currentIndex = -1;

    IView view;
    Activity activity;

    public TaskPresenter(TaskSession session, int index) {
        currentSession = session;
        currentIndex = index;
        currentTask = session.getTask(currentIndex);
    }

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

    protected void initView() {
        activity = view.getCurrentContext();
        TextView description = (TextView) activity.findViewById(R.id.description);
        description.setText(currentTask.getSummary());
        description.setTextSize(20);
        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.block);
        initLayout(layout);
    }

    protected abstract void initLayout(LinearLayout layout);

    protected void nextTask() {
        if (currentOption == null) {
            showToast(activity, "Enter answer! Please");
            return;
        }
        currentTask.checkTask(currentOption);
        currentIndex++;
        if (currentIndex >= currentSession.getGenerategData().size()) {
            redirectView(ProfileNewActivity.class, TemplateMethods.formatParameters(
                    new Tuple<String, Serializable>("session", currentSession)
            ));
        } else {
            currentOption = null;
            redirectView(TaskActivity.class, TemplateMethods.formatParameters(
                    new Tuple<String, Serializable>("session", currentSession),
                    new Tuple<String, Serializable>("type", currentSession.getSessionType()),
                    new Tuple<String, Serializable>("index", currentIndex++)
            ));
            activity.finish();
        }
    }

    @Override
    public void redirectView(Class<?> aClass, Map<String, Serializable> extras) {
        redirectToIntent(activity, aClass, false, extras);
    }
}
