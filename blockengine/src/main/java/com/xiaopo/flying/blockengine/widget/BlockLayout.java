package com.xiaopo.flying.blockengine.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.xiaopo.flying.blockengine.layout.Block;
import com.xiaopo.flying.blockengine.layout.PuzzleLayout;
import java.util.HashMap;

/**
 * @author wupanjie
 */
public class BlockLayout extends ViewGroup {
  private static final String TAG = "BlockLayout";
  private static final int DEFAULT_CHILD_GRAVITY = Gravity.TOP | Gravity.START;

  private PuzzleLayout puzzleLayout;
  private Rect blockRect;
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
    blockRect = new Rect();
    viewBlockMap = new HashMap<>();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (puzzleLayout != null && puzzleLayout.getBlockSize() > 0) {
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
        LayoutParams lp = (LayoutParams) child.getLayoutParams();

        if (viewBlockMap.containsKey(child)) {
          int blockPosition = viewBlockMap.get(child);
          block = puzzleLayout.getBlock(blockPosition);
        }

        int measureWidth = block.width() - lp.leftMargin - lp.rightMargin;
        int measureHeight = block.height() - lp.topMargin - lp.bottomMargin;

        measureChild(child, MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY));
      }
    }
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    if (puzzleLayout == null) {
      Log.e(TAG, "onLayout: the puzzle layout can not be null");
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

      layoutChildInBlock(child, block);
    }
  }

  private void layoutChildInBlock(View child, Block block) {
    if (child.getVisibility() == GONE) return;

    final LayoutParams lp = (LayoutParams) child.getLayoutParams();

    int gravity = lp.gravity;
    if (gravity == -1) {
      gravity = DEFAULT_CHILD_GRAVITY;
    }

    final int layoutDirection = getLayoutDirection();
    final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
    final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

    final int width = child.getMeasuredWidth();
    final int height = child.getMeasuredHeight();

    int childLeft;
    int childTop;

    final int parentLeft = block.left();
    final int parentTop = block.top();
    final int parentRight = block.right();
    final int parentBottom = block.bottom();

    switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
      case Gravity.CENTER_HORIZONTAL:
        childLeft =
            parentLeft + (parentRight - parentLeft - width) / 2 + lp.leftMargin - lp.rightMargin;
        break;
      case Gravity.RIGHT:
        childLeft = parentRight - width - lp.rightMargin;
        break;
      case Gravity.LEFT:
      default:
        childLeft = parentLeft + lp.leftMargin;
    }

    switch (verticalGravity) {
      case Gravity.TOP:
        childTop = parentTop + lp.topMargin;
        break;
      case Gravity.CENTER_VERTICAL:
        childTop =
            parentTop + (parentBottom - parentTop - height) / 2 + lp.topMargin - lp.bottomMargin;
        break;
      case Gravity.BOTTOM:
        childTop = parentBottom - height - lp.bottomMargin;
        break;
      default:
        childTop = parentTop + lp.topMargin;
    }

    Log.d(TAG, "layoutChildInBlock: left:" + childLeft + ",top:" + childTop + ",right:" + (childLeft
        + width) + ",bottom:" + (childTop + height));
    child.layout(childLeft, childTop, childLeft + width, childTop + height);
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new LayoutParams(getContext(), attrs);
  }

  @Override protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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

  public static class LayoutParams extends FrameLayout.LayoutParams {

    public LayoutParams(Context c, AttributeSet attrs) {
      super(c, attrs);
    }

    public LayoutParams(int width, int height) {
      super(width, height);
    }

    public LayoutParams(int width, int height, int gravity) {
      super(width, height);
      this.gravity = gravity;
    }

    public LayoutParams(MarginLayoutParams source) {
      super(source);
    }

    public LayoutParams(ViewGroup.LayoutParams source) {
      super(source);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT) public LayoutParams(LayoutParams source) {
      super(source);
      this.gravity = source.gravity;
    }
  }

  public void printInfo() {
    int i = 0;
    for (Block block : puzzleLayout.getBlocks()) {
      Log.d(TAG, "printInfo: block --> " + i++);
      Log.d(TAG, "printInfo: block --> " + block.getRect().toString());
    }
  }
}
