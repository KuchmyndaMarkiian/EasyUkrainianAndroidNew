package Models.ModelsFromServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mark0 on 30.04.2017.
 */
public class TopicJson {
    @SerializedName("Id")
    public int id;
    @SerializedName("Text")
    public String text;
    @SerializedName("Transcription")
    public String transcription;
    @SerializedName("Translate")
    public String translate;
    @SerializedName("TranslateImageId")
    public int translateImageId;
}