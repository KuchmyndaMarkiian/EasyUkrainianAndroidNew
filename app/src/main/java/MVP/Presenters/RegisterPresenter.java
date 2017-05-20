package MVP.Presenters;

import Hardware.WiFiConnector;
import Infrastructure.Interfaces.IConfirmedData;
import Infrastructure.Interfaces.IModelChecking;
import Infrastructure.Interfaces.IUserForm;
import Infrastructure.Interfaces.IValidating;
import Infrastructure.RESTful.Autorization.AutorizationService;
import MVP.Views.IView;
import Models.AutorizationModels.Abstract.EditingModel;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.example.mark0.easyukrainian.ProfileNewActivity;

import java.util.Objects;

import static Infrastructure.Static.EasyUkrApplication.showToast;

/**
 * Created by mark0 on 24.04.2017.
 */
public class RegisterPresenter extends AutorizationPresenter implements IRedirectablePresenter, IUserForm, IConfirmedData, IModelChecking, IValidating {
    IView view;
    EditingModel model;
    public RegisterPresenter() {
        model = new EditingModel();
    }

    @Override
    public boolean checkModel() {
        if(!super.checkModel())
            return false;

        try {
            if (Objects.equals(model.getName(), ""))
                throw new Exception("Name is empty");
            if (Objects.equals(model.getSurname(), ""))
                throw new Exception("Surname is empty");
            if (!isValidEmail(model.getEmail()))
                throw new Exception("E-Mail is empty or incorrect");
            if (Objects.equals(model.getConfirmPassword(), ""))
                throw new Exception("Confirm password is empty");
        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getCurrentContext());
            builder.setTitle("Error")
                    .setMessage(ex.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
            return false;
        }
        return true;
    }
    public void register() {
        if (checkModel()) {
            String message = null;
            Boolean result = false;
            if ((new WiFiConnector(view.getCurrentContext().getBaseContext()).isConnected())) {
                AutorizationService service = new AutorizationService(activity);
                service.registerModel(model);
                result = service.isSuccessfull();
                message = service.getAutorizationMessage();
            } else {
                message = "Wifi isn`t connected";
            }
            showToast(activity, (result ? "Successful! " : "Error! ") + message);
            if(result)
                redirectView(ProfileNewActivity.class, null);
        }
    }
    //region Setters
    @Override
    public void setName(String text) {
        model.setName(text);
    }


    @Override
    public void setSurname(String text) {
        model.setSurname(text);
    }

    @Override
    public void setUsername(String name) {
        model.setUsername(name);
    }

    @Override
    public void setPassword(String text) {
        model.setPassword(text);
    }

    @Override
    public void setConfirmPassword(String text) {
        model.setConfirmPassword(text);
    }

    @Override
    public void setEmail(String text) {
        model.setEmail(text);
    }

    @Override
    public void setDateOfBirth(int d, int m, int y) {
        model.setDateOfBirth(d, m, y);
    }

    //endregion
    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}