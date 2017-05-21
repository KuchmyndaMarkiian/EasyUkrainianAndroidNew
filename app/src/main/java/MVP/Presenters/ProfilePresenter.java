package MVP.Presenters;

import Hardware.SharedPreferences.UserPreference;
import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.User;
import Infrastructure.Static.EasyUkrApplication;
import Infrastructure.Tasks.Sessions.ITaskSession;
import MVP.Views.IProfileView;
import MVP.Views.IView;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mark0.easyukrainian.ProfileNewActivity;
import com.example.mark0.easyukrainian.R;

import java.io.Serializable;
import java.util.Map;

import static Infrastructure.Static.EasyUkrApplication.redirectToIntent;
import static Infrastructure.Static.EasyUkrApplication.showToast;

/**
 * Created by mark0 on 28.04.2017.
 */
public class ProfilePresenter implements IPresenter, IRedirectablePresenter {
    IProfileView view;
    private NavigationView navigationView;
    private ITaskSession session;
    private Activity activity;

    public ProfilePresenter(ITaskSession session) {
        this.session = session;
    }

    public void init() {
        if (session != null) {
            int result = session.getResult();
            CurrentUser.getInstance().setScore(result);
            showToast(activity, session.getResultMessage());
            UpdateAsync updateAsync = new UpdateAsync();
            updateAsync.execute();
        }
        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(view);

    }

    private void presentModel() {
        User user = CurrentUser.getInstance();
        TextView text = ((TextView) activity.findViewById(R.id.userHeader));
        String fullName = user.getName() + " " + user.getSurname();
        text.setText(fullName);
        text = ((TextView) activity.findViewById(R.id.userScoreHeader));
        String score = String.valueOf(user.getScore()).concat("/").concat(String.valueOf(user.getMaxScore()));
        text.setText(score);
        ImageView imageView = ((ImageView) activity.findViewById(R.id.userAvatar));
        imageView.setImageBitmap(EasyUkrApplication.getBitmap(user.getAvatar()));

        float percent = (float) user.getScore() / user.getMaxScore();

        View progressBar = activity.findViewById(R.id.progressBar);
        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) progressBar.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = percent;
        progressBar.requestLayout();

        View navView = navigationView.getHeaderView(0);
        text = (TextView) navView.findViewById(R.id.userDrawerText);
        text.setText(fullName);
        text = (TextView) navView.findViewById(R.id.mailDrawerText);
        text.setText(user.getEmail());
        imageView = (ImageView) navView.findViewById(R.id.imageDrawerAvatar);
        imageView.setImageBitmap(EasyUkrApplication.getBitmap(user.getAvatar()));
    }

    @Override
    public void redirectView(Class<?> aClass, Map<String, Serializable> extras) {
        redirectToIntent(view.getCurrentContext(), aClass, false, extras);
    }

    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {
        this.view = (ProfileNewActivity) view;
        activity = this.view.getCurrentContext();
        init();
        presentModel();
    }

    private class UpdateAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Activity activity = getView().getCurrentContext();
            CurrentUser.updateInfoToServer(activity);
            CurrentUser.updateInfoFromServer(activity);
            UserPreference.storeUserAccount();
            CurrentUser.getInstance().cloneFromMemory(UserPreference.readUserAccount());
            return null;
        }
    }
}
