package Models.LearningResources;

import java.io.Serializable;

/**
 * Created by Markan on 08.02.2017.
 */
public class Word implements Serializable {
    private byte[] image;
    private String word;
    private String translate;
    private String transcription;
    private Integer parent;
    private Integer id;

    public Word(Integer parent, Integer id, String word, String transcription, String translate, byte[] image) {
        this.parent = parent;
        this.id = id;
        this.word = word;
        this.translate = translate;
        this.image = image;
        this.transcription = transcription;
    }

    public String getTranscription() {
        return String.format("[%s]", transcription);
    }

    public Integer getParent() {
        return parent;
    }

    public Integer getID() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }

    public byte[] getImage() {
        return image;
    }
}
