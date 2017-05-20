package Models.ModelsFromServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MARKAN on 04.05.2017.
 */
public class GrammarJson {
    @SerializedName("Id")
    public Integer id;
    @SerializedName("Text")
    public String text;
    @SerializedName("Translate")
    public String translate;
}
