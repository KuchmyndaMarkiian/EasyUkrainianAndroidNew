package Models.ModelsFromServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mark0 on 30.04.2017.
 */
public class WordJson extends TopicJson {
    @SerializedName("ParentId")
    public int parentId;
}