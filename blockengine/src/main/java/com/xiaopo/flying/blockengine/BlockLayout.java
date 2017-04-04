package com.xiaopo.flying.blockengine;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;

/**
 * @author wupanjie
 */

public class BlockLayout extends ViewGroup {
  private static final String TAG = "BlockLayout";

  private PuzzleLayout puzzleLayout;
  private RectF blockRect;
  private HashMap<View, Integer> viewBlockMap;

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

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BlockLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    blockRect = new RectF();
    viewBlockMap = new HashMap<>();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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

      for (int i = 0; i < childSize; i++) {
        final View child = getChildAt(i);
        Block block = puzzleLayout.getBlock(i % blockSize);

        if (viewBlockMap.containsKey(child)) {
          int blockPosition = viewBlockMap.get(child);
          block = puzzleLayout.getBlock(blockPosition);
        }

        Log.d(TAG, "onMeasure: (" + block.width() + "," + block.height() + ")");
        measureChild(child, MeasureSpec.makeMeasureSpec((int) block.width(), widthMode),
            MeasureSpec.makeMeasureSpec((int) block.height(), heightMode));
      }
    }
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    if (puzzleLayout == null) {
      Log.d(TAG, "onLayout: the puzzle layout can not be null");
      return;
    }

    final int blockSize = puzzleLayout.getBlockSize();
    final int childSize = getChildCount();

    for (int i = 0; i < childSize; i++) {
      final View child = getChildAt(i);
      Block block = puzzleLayout.getBlock(i % blockSize);

      if (viewBlockMap.containsKey(child)) {
        int blockPosition = viewBlockMap.get(child);
        block = puzzleLayout.getBlock(blockPosition);
      }

      final MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

      layoutChild(child, block.left() + params.leftMargin, block.top() + params.topMargin,
          block.right() - params.rightMargin, block.bottom() - params.bottomMargin);
    }
  }

  public void layoutChild(final View child, float left, float top, float right, float bottom) {
    Log.d(TAG, "layoutChild: (" + left + "," + top + "," + right + "," + bottom + ")");
    child.layout((int) left, (int) top, (int) right, (int) bottom);
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(), attrs);
  }

  @Override protected LayoutParams generateDefaultLayoutParams() {
    return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    blockRect.left = getPaddingLeft();
    blockRect.top = getPaddingTop();
    blockRect.right = w - getPaddingRight();
    blockRect.bottom = h - getPaddingBottom();

    Log.d(TAG, "onSizeChanged: (" + w + "," + h + ")");

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

  public void addViewAtBlock(final View view, final int blockPosition) {
    if (puzzleLayout == null) {
      Log.e(TAG, "addViewAtBlock: the puzzle layout is null");
      return;
    }

    if (blockPosition >= puzzleLayout.getBlockSize()) {
      Log.e(TAG, "addViewAtBlock: the block position is greater than layout's block size");
      return;
    }

    viewBlockMap.put(view, blockPosition);
    addView(view);
  }
}
