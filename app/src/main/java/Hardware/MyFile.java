package Hardware;

import java.io.Serializable;

public class MyFile implements Serializable {
    String name;
    byte[] content;

    public MyFile(String name, byte[] file) {
        this.name = name;
        this.content = file;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }
}
