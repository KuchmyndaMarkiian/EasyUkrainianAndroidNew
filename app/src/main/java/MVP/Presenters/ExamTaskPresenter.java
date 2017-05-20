package MVP.Presenters;

import Infrastructure.Tasks.Sessions.TaskSession;
import Infrastructure.Tasks.Tasks.GrammarTask;
import Infrastructure.Tasks.Tasks.GuessTask;
import Infrastructure.Tasks.Tasks.PictureTask;
import Infrastructure.Tasks.Tasks.WordTask;
import android.widget.LinearLayout;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class ExamTaskPresenter extends TaskPresenter {


    public ExamTaskPresenter(TaskSession session, int index) {
        super(session, index);
    }

    protected void initLayout(LinearLayout layout) {
        if (currentTask == null)
            return;
        if (currentTask instanceof GrammarTask) {
            TaskPresenter grammarPresenter = new GrammarTaskPresenter(currentSession, currentIndex);
            grammarPresenter.setView(view);
        } else if (currentTask instanceof PictureTask
                || currentTask instanceof WordTask
                || currentTask instanceof GuessTask) {
            TaskPresenter vocabularyPresenter = new VocabularyTaskPresenter(currentSession, currentIndex);
            vocabularyPresenter.setView(view);
        }
    }
}
