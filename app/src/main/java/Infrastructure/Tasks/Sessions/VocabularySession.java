package Infrastructure.Tasks.Sessions;

import Hardware.Storage.EasyUkrFiles;
import Infrastructure.Serialization.Deserializer;
import Infrastructure.Tasks.Tasks.GuessTask;
import Infrastructure.Tasks.Tasks.PictureTask;
import Infrastructure.Tasks.Tasks.WordTask;
import Models.LearningResources.Word;
import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Markan on 25.02.2017.
 */
public class VocabularySession extends TaskSession implements Serializable {

    @Override
    public String getResultMessage() {
        return "Your result: "
                .concat(String.valueOf(getResult()))
                .concat("/9.0");
    }

    @Override
    public void generate(Activity activity) {
        ArrayList<PictureTask<Integer>> pictureTasks = new ArrayList<>();
        ArrayList<WordTask<Integer>> wordTasks = new ArrayList<>();
        ArrayList<GuessTask<String>> guessTasks = new ArrayList<>();
        Deserializer<Word> deserializer = new Deserializer<>(EasyUkrFiles.Type.WORD, EasyUkrFiles.defaultpath);
        ArrayList<Word> list = deserializer.readObject();
        ArrayList<Word> filter = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        //region PICTURES
        for (int i = 0; i < 3; i++) {
            while (filter.size() < 4) {
                Word tmp = list.get(random.nextInt(list.size() - 1));
                if (!filter.contains(tmp))
                    filter.add(tmp);
            }
            int index = random.nextInt(filter.size() - 1);
            String[] options = new String[filter.size() - 1];
            for (int j = 0; j < options.length; j++)
                options[j] = filter.get(j).getWord();
            pictureTasks.add(new PictureTask<>(
                    filter.get(index).getImage(),
                    index, options
            ));
        }
        //endregion
        filter.clear();
        random = new Random(System.currentTimeMillis());
        //region WORD
        for (int i = 0; i < 3; i++) {
            while (filter.size() < 4) {
                Word tmp = list.get(random.nextInt(list.size() - 1));
                if (!filter.contains(tmp))
                    filter.add(tmp);
            }
            int index = random.nextInt(filter.size() - 1);
            String[] options = new String[filter.size() - 1];
            for (int j = 0; j < options.length; j++)
                options[j] = filter.get(j).getTranslate();
            wordTasks.add(new WordTask<>(
                    filter.get(index).getWord(),
                    index, options
            ));
        }
        //endregion
        filter.clear();
        random = new Random(System.currentTimeMillis());
        //region GUESS
        for (int i = 0; i < 3; i++) {
            Word tmp = list.get(random.nextInt(list.size() - 1));
            guessTasks.add(new GuessTask<>(
                    tmp.getImage(), tmp.getWord()));
        }
        //endregion
        tasks.addAll(pictureTasks);
        tasks.addAll(wordTasks);
        tasks.addAll(guessTasks);
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.VOCABULARY;
    }
}
