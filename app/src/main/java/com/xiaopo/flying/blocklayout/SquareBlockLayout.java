package com.xiaopo.flying.blocklayout;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopo.flying.blockengine.widget.BlockLayout;

/**
 * @author wupanjie
 */

public class SquareBlockLayout extends BlockLayout {
  public SquareBlockLayout(Context context) {
    super(context);
  }

  public SquareBlockLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SquareBlockLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int width = getMeasuredWidth();
    int height = getMeasuredHeight();
    int length = width > height ? height : width;

    setMeasuredDimension(length, length);
  }
}
