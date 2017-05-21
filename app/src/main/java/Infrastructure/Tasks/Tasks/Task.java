package Infrastructure.Tasks.Tasks;

import java.io.Serializable;

/**
 * Created by Markan on 25.02.2017.
 */
public abstract class Task<T> implements Serializable {
    public boolean isCorrect = false;
    String summary;
    Object content;
    Object options;
    T correctAnswer;

    public void checkTask(T object) {
        isCorrect = correctAnswer.equals(object);
    }

    public String getSummary() {
        return summary;
    }

    public Object getContent() {
        return content;
    }

    public Object getOptions() {
        return options;
    }
}
