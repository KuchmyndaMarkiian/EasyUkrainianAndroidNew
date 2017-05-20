package Infrastructure.Serialization;

import Hardware.Storage.EasyUkrFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Markan on 14.02.2017.
 */
public class Serializer {
    String path = null;
    File dir = null;
    public EasyUkrFiles.Type mainType;
    private ObjectOutputStream outputStream;

    public Serializer(EasyUkrFiles.Type type, File dir) {
        mainType = type;

        path = EasyUkrFiles.setFile(mainType);
        this.dir = dir;

        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(new File(this.dir, path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
