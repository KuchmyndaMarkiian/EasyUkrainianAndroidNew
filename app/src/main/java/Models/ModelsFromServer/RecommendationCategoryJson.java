package Models.ModelsFromServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MARKAN on 10.05.2017.
 */
public class RecommendationCategoryJson {
    @SerializedName("Id")
    public Integer id;
    @SerializedName("Text")
    public String text;
    @SerializedName("Translate")
    public String translate;
}
