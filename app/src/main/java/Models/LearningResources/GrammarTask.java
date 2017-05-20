package Models.LearningResources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MARKAN on 10.05.2017.
 */
public class GrammarTask implements Serializable {
    @SerializedName("Text")
    public String text;
    @SerializedName("Translate")
    public String translate;
    @SerializedName("Description")
    public String description;
    @SerializedName("Answers")
    public List<GrammarAnswer> answers = null;

    public GrammarTask(String text, String translate, String description, List<GrammarAnswer> list) {
        this.answers = list;
        this.description = description;
        this.text = text;
        this.translate = translate;
    }
}
