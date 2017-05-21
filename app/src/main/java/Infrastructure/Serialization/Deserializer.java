package Infrastructure.Serialization;

import Hardware.Storage.EasyUkrFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Markan on 14.02.2017.
 */
public class Deserializer<T> {

    public EasyUkrFiles.Type type;
    private ObjectInputStream inputStream;

    public Deserializer(EasyUkrFiles.Type type, File dir) {

        this.type = type;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(new File(dir, EasyUkrFiles.setFile(type))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<T> readObject() {
        try {
            return (ArrayList<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
