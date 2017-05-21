package Infrastructure.Tasks.Tasks;

import java.io.Serializable;

/**
 * Created by Markan on 25.02.2017.
 */
public class GuessTask<T> extends Task<T> implements Serializable {


    public GuessTask(byte[] image, T answer) {
        this.summary = "What is it on the picture?";
        this.content = image;
        this.correctAnswer = answer;
    }
}
