package com.example.mark0.easyukrainian;

import Infrastructure.MainOperations.ServerDownloader;
import Infrastructure.RESTful.Autorization.AutorizationService;
import Infrastructure.RESTful.Autorization.AutorizationServiceNew;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.HTTP.HttpManager;
import Models.AutorizationModels.Abstract.EditingModel;
import Models.AutorizationModels.Abstract.UserModel;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void loginNew() throws Exception {
        UserModel model = new UserModel();
        model.setUsername("mark");
        model.setPassword("Mark95!");

        AutorizationServiceNew serviceNew = new AutorizationServiceNew(null);
        serviceNew.login(model);

    }

    @Test
    public void getImageTest() throws Exception {
        byte[] img =
                ServerDownloader.getFile(null, ConstURL.getFileUrl(), HttpManager.ParameterType.PARAMETER, "word", "6");

    }

    @Test
    public void register() throws Exception {

        EditingModel model = new EditingModel();
        model.setEmail("mark0611@ukr.net");
        model.setName("mark");
        model.setSurname("mark");
        model.setPassword("Mark95!");
        model.setConfirmPassword("Mark95!");
        model.setDateOfBirth(6, 11, 1995);
        model.setUsername("markan");
        //model.setAvatar(new byte[]{0, 0, 34});
        String Message = "Cool";
        AutorizationService service = new AutorizationService(null);
        service.registerModel(model);
        if (!service.isSuccessfull())
            Message = service.getAutorizationMessage();


        System.out.print(Message);
    }
}
