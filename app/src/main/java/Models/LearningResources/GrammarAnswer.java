package Models.LearningResources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by MARKAN on 13.05.2017.
 */
public class GrammarAnswer implements Serializable {
    @SerializedName("Text")
    public String text;
    @SerializedName("IsCorrect")
    public Boolean isCorrect;
}