package Models.LearningResources;

import java.io.Serializable;

/**
 * Created by Markan on 04.03.2017.
 */
public class Recommendation implements Serializable {
    private byte[] image;
    private String link;
    private String header;
    private String translate;
    private Integer parent;

    public Recommendation(String header, String translate, String link, byte[] image, int parent) {

        this.header = header;
        this.translate = translate;
        this.link = link;
        this.image = image;
        this.parent = parent;
    }

    public Integer getParent() {
        return parent;
    }


    public String getHeader() {
        return header;
    }

    public String getTranslate() {
        return translate;
    }

    public String getLink() {
        return link;
    }

    public byte[] getImage() {
        return image;
    }
}
