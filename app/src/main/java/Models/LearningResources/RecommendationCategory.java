package Models.LearningResources;

import java.io.Serializable;

/**
 * Created by Markan on 04.03.2017.
 */
public class RecommendationCategory implements Serializable {
    private byte[] image;
    private String header;
    private String translate;
    private Integer id;

    public RecommendationCategory(Integer id, String header, String translate, byte[] image) {
        this.id = id;
        this.header = header;
        this.translate = translate;
        this.image = image;
    }

    public Integer getID() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getTranslate() {
        return translate;
    }

    public byte[] getImage() {
        return image;
    }
}
