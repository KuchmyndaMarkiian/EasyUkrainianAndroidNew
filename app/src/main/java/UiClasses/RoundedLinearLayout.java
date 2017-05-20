package UiClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.example.mark0.easyukrainian.R;

/**
 * Created by Markan on 08.02.2017.
 */
public class RoundedLinearLayout extends LinearLayout {

    private RectF rect;
    private Paint paint;

    public RoundedLinearLayout(Context context) {
        super(context);
        init();
    }

    public RoundedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int angle = R.dimen.roundCorner;
        canvas.drawRoundRect(rect, angle, angle, paint);
    }

    private void init() {
        rect = new RectF(0f, 0f, getWidth(), getHeight());
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#fafafa"));
    }
}
