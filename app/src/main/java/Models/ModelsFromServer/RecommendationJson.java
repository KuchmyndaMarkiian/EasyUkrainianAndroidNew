package Models.ModelsFromServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MARKAN on 10.05.2017.
 */
public class RecommendationJson {
    @SerializedName("Id")
    public String id;
    @SerializedName("Text")
    public String text;
    @SerializedName("Translate")
    public String translate;
    @SerializedName("UrlLink")
    public String urlLink;
    @SerializedName("ParentId")
    public Integer parentId;
}
