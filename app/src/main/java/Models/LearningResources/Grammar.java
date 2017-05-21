package Models.LearningResources;

import Hardware.MyFile;

import java.io.Serializable;

/**
 * Created by Markan on 23.02.2017.
 */
public class Grammar implements Serializable {
    MyFile file;
    private String header;
    private String translate;

    public Grammar(String header, String translate, byte[] file) {
        this.header = header;
        this.translate = translate;
        this.file = new MyFile(header + ".pdf", file);
    }

    public MyFile getFile() {
        return file;
    }

    public String getHeader() {
        return header;
    }

    public String getTranslate() {
        return translate;
    }
}
