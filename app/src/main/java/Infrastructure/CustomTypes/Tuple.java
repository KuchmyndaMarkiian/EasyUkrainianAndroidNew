package Infrastructure.CustomTypes;

/**
 * Created by MARKAN on 13.05.2017.
 */
public class Tuple<F, S> {
    public F key;
    public S value;

    public Tuple(F f, S s) {
        key = f;
        value = s;
    }
}