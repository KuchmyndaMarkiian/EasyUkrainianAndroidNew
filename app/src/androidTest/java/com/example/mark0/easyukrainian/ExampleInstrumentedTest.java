package com.example.mark0.easyukrainian;

import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.RESTful.Autorization.AutorizationService;
import Infrastructure.RESTful.ConstURL;
import Infrastructure.RESTful.WebAPI.WebApiGet;
import Models.AutorizationModels.Abstract.EditingModel;
import Models.AutorizationModels.Abstract.UserModel;
import Models.ModelsFromServer.TopicJson;
import Models.SimpleUser;
import android.support.test.runner.AndroidJUnit4;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void login() throws Exception {
        UserModel model = new UserModel();
        model.setUsername("markan");
        model.setPassword("Mark95!");

        String Message = "Cool";
        AutorizationService service = new AutorizationService(null);
        service.loginModel(model);
        if (!service.isSuccessfull())
            Message = service.getAutorizationMessage();


        System.out.print(Message);
    }

    @Test
    public void register() throws Exception {

        EditingModel model = new EditingModel();
        model.setEmail("mark0611ukr.net");
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

    @Test
    public void getUser() throws Exception {
        SimpleUser user;
        WebApiGet apiGet = new WebApiGet();
        //register();
        login();
        user = apiGet.getUserDetails(CurrentUser.getInstance().getToken().getToken());
    }

    @Test
    public void asyncTest() throws Exception {

        /*OkHttpAsync async = new OkHttpAsync(/*ConstURL.getGrammarTasksUrl()*);
        async.run();
        ArrayList<TopicJson> list = null;
        while (list == null)
            list = (new Gson()).fromJson(async.awaitResult().body().string(), new TypeToken<ArrayList<TopicJson>>() {
            }.getType());
        int a = list.size();*/
    }

    @Test
    public void asyncTest2() throws Exception {

        ArrayList<TopicJson> elements = (ArrayList<TopicJson>) (new WebApiGet()).getContent(ConstURL.getTopicUrl(),
                new TypeToken<ArrayList<TopicJson>>() {
                }.getType());
        int a = elements.size();
    }

}
