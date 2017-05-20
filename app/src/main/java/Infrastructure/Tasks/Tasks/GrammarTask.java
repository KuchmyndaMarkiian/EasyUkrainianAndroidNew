package Infrastructure.Tasks.Tasks;

import java.io.Serializable;

/**
 * Created by MARKAN on 20.05.2017.
 */
public class GrammarTask<T> extends Task<T> implements Serializable {
    public GrammarTask(String summary, Object content, Object options, T correctAnswer) {
        this.summary = summary;
        this.content = content;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}
