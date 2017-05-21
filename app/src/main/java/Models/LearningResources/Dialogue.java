package Models.LearningResources;

import java.io.Serializable;

/**
 * Created by MARKAN on 19.05.2017.
 */
public class Dialogue implements Serializable {
    private Integer id;
    private String header;
    private String dialogueUkr;
    private String dialogueEng;
    private byte[] image;

    public Dialogue(Integer id, String header, String dialogueUkr, String dialogueEng, byte[] image) {
        this.id = id;
        this.header = header;
        this.dialogueUkr = dialogueUkr;
        this.dialogueEng = dialogueEng;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDialogueEng() {
        return dialogueEng;
    }

    public String getDialogueUkr() {
        return dialogueUkr;
    }

    public String getHeader() {
        return header;
    }
}