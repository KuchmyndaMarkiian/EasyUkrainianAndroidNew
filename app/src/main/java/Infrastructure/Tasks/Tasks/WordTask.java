package Infrastructure.Tasks.Tasks;

import java.io.Serializable;

/**
 * Created by Markan on 25.02.2017.
 */
public class WordTask<T> extends Task<T> implements Serializable {

    public WordTask(String image, T index, String[] options) {
        this.summary = "Translate it from ENG to UKR";
        this.content = image;
        correctAnswer = index;
        this.options = options;
    }
}
