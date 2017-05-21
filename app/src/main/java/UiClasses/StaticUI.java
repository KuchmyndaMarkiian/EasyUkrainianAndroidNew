package UiClasses;

import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;

/**
 * Created by Markan on 25.02.2017.
 */
public class StaticUI {
    public static Visibility getAnimation() {
        Visibility anim = null;
        switch (Animation.EXPLODE) {
            case EXPLODE:
                anim = new Explode();
                break;
            case FADE:
                anim = new Fade();
                break;
            case SLIDE:
                anim = new Slide();
        }
        anim.setDuration(1000);
        return anim;
    }

    public enum Animation {FADE, EXPLODE, SLIDE}
}