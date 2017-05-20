package Infrastructure.Tasks.Sessions;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MARKAN on 18.05.2017.
 */
public interface ITaskSession<T> extends Serializable {
    int getResult();

    String getResultMessage();

    void generate(Activity activity);

    ArrayList getGenerategData();

    SessionType getSessionType();
}
