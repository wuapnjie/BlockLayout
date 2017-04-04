package com.xiaopo.flying.blockengine;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author wupanjie
 */

public class BlockLayout extends ViewGroup {
  private static final String TAG = "BlockLayout";

  private PuzzleLayout puzzleLayout;
  private RectF blockRect;

  public BlockLayout(Context context) {
    this(context, null, 0);
  }

  public BlockLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BlockLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    blockRect = new RectF();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BlockLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    Log.d(TAG, "onMeasure: ");
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (puzzleLayout != null) {
      final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
      final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

      final int width = getMeasuredWidth();
      final int height = getMeasuredHeight();

      blockRect.left = getPaddingLeft();
      blockRect.top = getPaddingTop();
      blockRect.right = width - getPaddingRight();
      blockRect.bottom = height - getPaddingBottom();

      puzzleLayout.reset();
      puzzleLayout.setOuterBlock(blockRect);
      puzzleLayout.layout();

      final int blockSize = puzzleLayout.getBlockSize();
      final int childSize = getChildCount();
      final int size = blockSize > childSize ? childSize : blockSize;

      for (int i = 0; i < size; i++) {
        final Block block = puzzleLayout.getBlock(i);
        final View child = getChildAt(i);

        Log.d(TAG, "onMeasure: (" + block.width() + "," + block.height() + ")");
        measureChild(child, MeasureSpec.makeMeasureSpec((int) block.width(), widthMode),
            MeasureSpec.makeMeasureSpec((int) block.height(), heightMode));
      }
    }
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    Log.d(TAG, "onLayout: ");
    if (puzzleLayout == null) {
      Log.d(TAG, "onLayout: the puzzle layout can not be null");
      return;
    }

    final int blockSize = puzzleLayout.getBlockSize();
    final int childSize = getChildCount();
    final int size = blockSize > childSize ? childSize : blockSize;

    for (int i = 0; i < size; i++) {
      final Block block = puzzleLayout.getBlock(i);
      final View child = getChildAt(i);

      layoutChild(child, block.left(), block.top(), block.right(), block.bottom());
    }
  }

  public void layoutChild(final View child, float left, float top, float right, float bottom) {
    Log.d(TAG, "layoutChild: (" + left + "," + top + "," + right + "," + bottom + ")");
    child.layout((int) left, (int) top, (int) right, (int) bottom);
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(), attrs);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.d(TAG, "onSizeChanged: ");
    blockRect.left = getPaddingLeft();
    blockRect.top = getPaddingTop();
    blockRect.right = w - getPaddingRight();
    blockRect.bottom = h - getPaddingBottom();

    if (puzzleLayout != null) {
      puzzleLayout.reset();
      puzzleLayout.setOuterBlock(blockRect);
      puzzleLayout.layout();

      requestLayout();
    }
  }

  public void setPuzzleLayout(PuzzleLayout puzzleLayout) {
    this.puzzleLayout = puzzleLayout;
    this.puzzleLayout.setOuterBlock(blockRect);
    this.puzzleLayout.layout();

    requestLayout();
  }
}
