package Infrastructure.CustomTypes;

/**
 * Created by MARKAN on 13.05.2017.
 */
public class ParameterPair<F, S> {
    public F key;
    public S value;

    public ParameterPair(F f, S s) {
        key = f;
        value = s;
    }
}