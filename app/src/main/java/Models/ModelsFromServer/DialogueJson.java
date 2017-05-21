package Models.ModelsFromServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MARKAN on 19.05.2017.
 */
public class DialogueJson {
    @SerializedName("Id")
    public Integer id;
    @SerializedName("Header")
    public String header;
    @SerializedName("DialogueUkr")
    public String dialogueUkr;
    @SerializedName("DialogueEng")
    public String dialogueEng;
}