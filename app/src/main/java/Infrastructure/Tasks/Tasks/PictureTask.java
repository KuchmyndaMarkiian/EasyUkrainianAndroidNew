package Infrastructure.Tasks.Tasks;

import java.io.Serializable;

/**
 * Created by Markan on 25.02.2017.
 */
public class PictureTask<T> extends Task<T> implements Serializable {
    public PictureTask(byte[] image, T index, String[] options) {
        this.summary = "What is it on the picture?";
        this.content = image;
        correctAnswer = index;
        this.options = options;
    }
}