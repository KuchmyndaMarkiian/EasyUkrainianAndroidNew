package Infrastructure.Tasks.Sessions;

import Hardware.Storage.EasyUkrFiles;
import Infrastructure.Serialization.Deserializer;
import Models.LearningResources.Word;
import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by MARKAN on 18.05.2017.
 */
public class GameSession implements ITaskSession, Serializable {

    private ArrayList<String> words;
    private ArrayList<Character> letters;
    private int checked = 0;
    private int wrong = 0;

    public GameSession(Activity activity) {
        words = new ArrayList<>();
        letters = new ArrayList<>();
    }

    public boolean checkAnagram(String word) {
        if (words.contains(word)) {
            checked++;
            return true;
        }
        wrong++;
        return false;
    }

    public boolean isFinished() {
        return checked == words.size();
    }

    @Override
    public int getResult() {
        return getLettersLength() * (checked - wrong / 2);
    }

    @Override
    public String getResultMessage() {
        return "Your result: "
                .concat(String.valueOf(getResult()))
                .concat("/")
                .concat(String.valueOf(getLettersLength() * checked));
    }

    @Override
    public void generate(Activity activity) {
        Deserializer deserializer = new Deserializer<Word>(EasyUkrFiles.Type.WORD, activity.getExternalFilesDir(null));
        ArrayList<Word> tmp = deserializer.readObject();
        for (Word word : tmp) {
            words.add(word.getWord());
        }

        //generating algorithm
        int length = 6;
        Random random = new Random(System.currentTimeMillis());
        int commonLength = length * length;
        while (true) {
            int index = random.nextInt(words.size());
            words.remove(index);
            if (getLettersLength() <= commonLength)
                break;
        }
        for (String word : words) {
            random = new Random(System.currentTimeMillis());
            ArrayList<Integer> indexes = new ArrayList<>();
            length = word.length();
            int index = 0;
            while (indexes.size() != length) {
                index = random.nextInt(length);
                if (!indexes.contains(index)) {
                    indexes.add(index);
                    letters.add(word.charAt(index));
                }
            }
        }
        shakeLetters();
    }

    private void shakeLetters() {
        ArrayList<Character> result = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        ArrayList<Integer> indexes = new ArrayList<>();
        int length = letters.size();
        int index = 0;
        while (indexes.size() != length) {
            index = random.nextInt(length);
            if (!indexes.contains(index)) {
                indexes.add(index);
                result.add(letters.get(index));
            }
        }
        letters = result;
    }

    private int getLettersLength() {
        int res = 0;
        for (String str : words) {
            res += str.length();
        }
        return res;
    }

    @Override
    public ArrayList<Character> getGenerategData() {
        return letters;
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.GAME;
    }
}
