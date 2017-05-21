package Infrastructure.Tasks.Sessions;

import android.app.Activity;

import java.io.Serializable;

import static Infrastructure.CustomTypes.TemplateMethods.shakeCollection;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class ExamSession extends TaskSession implements Serializable {
    @Override
    public String getResultMessage() {
        return "Your result: "
                .concat(String.valueOf(getResult()))
                .concat("/54.0");
    }

    @Override
    public int getResult() {
        return super.getResult() * 3;
    }

    @Override
    public void generate(Activity activity) {
        ITaskSession vocabularySession = new VocabularySession();
        vocabularySession.generate(activity);
        tasks.addAll(vocabularySession.getGenerategData());
        ITaskSession grammarSession = new GrammarSession();
        grammarSession.generate(activity);
        tasks.addAll(grammarSession.getGenerategData());
        tasks = shakeCollection(tasks);
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.EXAM;
    }
}
