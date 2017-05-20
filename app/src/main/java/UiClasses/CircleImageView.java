package UiClasses;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Markan on 06.02.2017.
 */
public class CircleImageView extends ImageView {
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public ScaleType getScaleType() {
        return super.getScaleType();
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //создаем круг
        final float halfWidth = canvas.getWidth() / 2;
        final float halfHeight = canvas.getHeight() / 2;
        final float radius = Math.max(halfWidth, halfHeight);
        final Path path = new Path();
        path.addCircle(halfWidth, halfHeight, radius, Path.Direction.CCW);

        //обрезаем
        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}
