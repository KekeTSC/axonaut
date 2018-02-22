package fr.axonaut.axonaut.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class SquareImageButton extends android.support.v7.widget.AppCompatImageButton {
    public SquareImageButton(Context context) {
        super(context);
    }

    public SquareImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //(1)if you want the height to match the width
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        //(2)if you want the width to match the height
        //super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}