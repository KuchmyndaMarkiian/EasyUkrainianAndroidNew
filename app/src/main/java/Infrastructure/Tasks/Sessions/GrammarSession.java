package Infrastructure.Tasks.Sessions;

import Hardware.Storage.EasyUkrFiles;
import Infrastructure.Serialization.Deserializer;
import Infrastructure.Tasks.Tasks.GrammarTask;
import Models.LearningResources.GrammarAnswer;
import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class GrammarSession extends TaskSession implements Serializable {

    @Override
    public int getResult() {
        return super.getResult() * 2;
    }

    @Override
    public String getResultMessage() {
        return "Your result: "
                .concat(String.valueOf(getResult()))
                .concat("/18.0");
    }

    @Override
    public void generate(Activity activity) {
        ArrayList<GrammarTask<Integer>> grammarTasks = new ArrayList<>();
        Deserializer<Models.LearningResources.GrammarTask> deserializer = new Deserializer<>(
                EasyUkrFiles.Type.GRAMMARTASK, activity.getExternalFilesDir(null)
        );
        ArrayList<Models.LearningResources.GrammarTask> gtasks = deserializer.readObject();
        Random random = new Random(System.currentTimeMillis());
        ArrayList<Models.LearningResources.GrammarTask> filter = new ArrayList<>();

        while (filter.size() < 9) {
            Models.LearningResources.GrammarTask gtask = gtasks.get(random.nextInt(gtasks.size()));
            if (!filter.contains(gtask))
                filter.add(gtask);
        }

        for (Models.LearningResources.GrammarTask task : filter) {
            Integer correctAnswer = -1;
            int index = 0;
            ArrayList<String> answers = new ArrayList<>();
            for (GrammarAnswer answer : task.answers) {
                answers.add(answer.text);
                if (answer.isCorrect)
                    correctAnswer = index;
                index++;
            }
            grammarTasks.add(new GrammarTask<>(task.translate, task.description, answers, correctAnswer));
        }
        tasks.addAll(grammarTasks);
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.GRAMMAR;
    }
}
