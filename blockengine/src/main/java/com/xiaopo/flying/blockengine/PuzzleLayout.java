package com.xiaopo.flying.blockengine;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * contains all lines and borders.
 * we can add line to divider a border to number of borders.
 * <p>
 * <p>
 * to determine the which border to layout puzzle piece.
 *
 * @see Block
 * <p>
 * @author wupanjie
 */
public abstract class PuzzleLayout {
  protected static final String TAG = "PuzzleLayout";

  protected int mTheme;

  private Block mOuterBlock;

  private List<Block> mBlocks = new ArrayList<>();
  private List<Line> mLines = new ArrayList<>();
  private List<Line> mOuterLines = new ArrayList<>(4);

  private Comparator<Block> mBorderComparator = new BlockComparator();

  public PuzzleLayout() {

  }

  public PuzzleLayout(RectF baseRect) {
    setOuterBlock(baseRect);
  }

  public void setOuterBlock(RectF baseRect) {
    PointF one = new PointF(baseRect.left, baseRect.top);
    PointF two = new PointF(baseRect.right, baseRect.top);
    PointF three = new PointF(baseRect.left, baseRect.bottom);
    PointF four = new PointF(baseRect.right, baseRect.bottom);

    Line lineLeft = new Line(one, three);
    Line lineTop = new Line(one, two);
    Line lineRight = new Line(two, four);
    Line lineBottom = new Line(three, four);

    mOuterLines.clear();

    mOuterLines.add(lineLeft);
    mOuterLines.add(lineTop);
    mOuterLines.add(lineRight);
    mOuterLines.add(lineBottom);

    mOuterBlock = new Block(baseRect);

    mBlocks.clear();
    mBlocks.add(mOuterBlock);
  }

  public abstract void layout();

  protected List<Block> addLine(Block block, Line.Direction direction, float ratio) {
    mBlocks.remove(block);
    Line line = BlockUtil.createLine(block, direction, ratio);
    mLines.add(line);

    List<Block> blockList = BlockUtil.cutBorder(block, line);
    mBlocks.addAll(blockList);

    updateLineLimit();
    Collections.sort(mBlocks, mBorderComparator);

    return blockList;
  }

  protected void cutBlockEqualPart(Block block, int part, Line.Direction direction) {
    Block temp = block;
    for (int i = part; i > 1; i--) {
      temp = addLine(temp, direction, (float) (i - 1) / i).get(0);
    }
  }

  protected List<Block> addCross(Block block, float radio) {
    return addCross(block, radio, radio);
  }

  protected List<Block> addCross(Block block, float horizontalRadio, float verticalRadio) {
    mBlocks.remove(block);
    Line horizontal = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, horizontalRadio);
    Line vertical = BlockUtil.createLine(block, Line.Direction.VERTICAL, verticalRadio);
    mLines.add(horizontal);
    mLines.add(vertical);

    List<Block> blockList = BlockUtil.cutBorderCross(block, horizontal, vertical);
    mBlocks.addAll(blockList);

    updateLineLimit();

    if (mBorderComparator == null) {
      mBorderComparator = new BlockComparator();
    }
    Collections.sort(mBlocks, mBorderComparator);

    return blockList;
  }

  protected List<Block> cutBlockEqualPart(Block block, int hSize, int vSize) {
    if ((hSize + 1) * (vSize + 1) > 9) {
      Log.e(TAG, "cutBorderEqualPart: the size can not be so great");
      return null;
    }
    mBlocks.remove(block);
    List<Block> blockList = new ArrayList<>();
    switch (hSize) {
      case 1:
        switch (vSize) {
          case 1:
            blockList.addAll(addCross(block, 1f / 2));
            break;
          case 2:
            Line l1 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 1f / 3);
            Line l2 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 2f / 3);
            Line l3 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 1f / 2);

            mLines.add(l1);
            mLines.add(l2);
            mLines.add(l3);

            blockList.addAll(BlockUtil.cutBorder(block, l1, l2, l3, Line.Direction.VERTICAL));
            break;

          case 3:
            Line ll1 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 1f / 4);
            Line ll2 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 2f / 4);
            Line ll3 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 3f / 4);
            Line ll4 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 1f / 2);

            mLines.add(ll1);
            mLines.add(ll2);
            mLines.add(ll3);
            mLines.add(ll4);

            blockList.addAll(
                BlockUtil.cutBorder(block, ll1, ll2, ll3, ll4, Line.Direction.VERTICAL));

            break;
        }
        break;

      case 2:
        switch (vSize) {
          case 1:
            Line l1 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 1f / 3);
            Line l2 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 2f / 3);
            Line l3 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 1f / 2);

            mLines.add(l1);
            mLines.add(l2);
            mLines.add(l3);

            blockList.addAll(BlockUtil.cutBorder(block, l1, l2, l3, Line.Direction.HORIZONTAL));

            break;
          case 2:
            Line ll1 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 1f / 3);
            Line ll2 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 2f / 3);
            Line ll3 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 1f / 3);
            Line ll4 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 2f / 3);

            mLines.add(ll1);
            mLines.add(ll2);
            mLines.add(ll3);
            mLines.add(ll4);

            blockList.addAll(BlockUtil.cutBorder(block, ll1, ll2, ll3, ll4));
            break;
        }
        break;

      case 3:
        switch (vSize) {
          case 1:
            Line ll1 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 1f / 4);
            Line ll2 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 2f / 4);
            Line ll3 = BlockUtil.createLine(block, Line.Direction.HORIZONTAL, 3f / 4);
            Line ll4 = BlockUtil.createLine(block, Line.Direction.VERTICAL, 1f / 2);

            mLines.add(ll1);
            mLines.add(ll2);
            mLines.add(ll3);
            mLines.add(ll4);

            blockList.addAll(
                BlockUtil.cutBorder(block, ll1, ll2, ll3, ll4, Line.Direction.HORIZONTAL));
            break;
        }
    }

    mBlocks.addAll(blockList);

    updateLineLimit();
    Collections.sort(mBlocks, mBorderComparator);

    return blockList;
  }

  protected List<Block> cutSpiral(Block block) {
    mBlocks.remove(block);
    List<Block> blockList = new ArrayList<>();

    float width = block.width();
    float height = block.height();

    PointF one = new PointF(0, height / 3);
    PointF two = new PointF(width / 3 * 2, 0);
    PointF three = new PointF(width, height / 3 * 2);
    PointF four = new PointF(width / 3, height);
    PointF five = new PointF(width / 3, height / 3);
    PointF six = new PointF(width / 3 * 2, height / 3);
    PointF seven = new PointF(width / 3 * 2, height / 3 * 2);
    PointF eight = new PointF(width / 3, height / 3 * 2);

    Line l1 = new Line(one, six);
    Line l2 = new Line(two, seven);
    Line l3 = new Line(eight, three);
    Line l4 = new Line(five, four);

    l1.setAttachLineStart(block.lineLeft);
    l1.setAttachLineEnd(l2);
    l1.setUpperLine(block.lineTop);
    l1.setLowerLine(l3);

    l2.setAttachLineStart(block.lineTop);
    l2.setAttachLineEnd(l3);
    l2.setUpperLine(block.lineRight);
    l2.setLowerLine(l4);

    l3.setAttachLineStart(l4);
    l3.setAttachLineEnd(block.lineRight);
    l3.setUpperLine(l1);
    l3.setLowerLine(block.lineBottom);

    l4.setAttachLineStart(l1);
    l4.setAttachLineEnd(block.lineBottom);
    l4.setUpperLine(l2);
    l4.setLowerLine(block.lineLeft);

    mLines.add(l1);
    mLines.add(l2);
    mLines.add(l3);
    mLines.add(l4);

    Block b1 = new Block(block);
    b1.lineRight = l2;
    b1.lineBottom = l1;
    blockList.add(b1);

    Block b2 = new Block(block);
    b2.lineLeft = l2;
    b2.lineBottom = l3;
    blockList.add(b2);

    Block b3 = new Block(block);
    b3.lineRight = l4;
    b3.lineTop = l1;
    blockList.add(b3);

    Block b4 = new Block(block);
    b4.lineTop = l1;
    b4.lineRight = l2;
    b4.lineLeft = l4;
    b4.lineBottom = l3;
    blockList.add(b4);

    Block b5 = new Block(block);
    b5.lineLeft = l4;
    b5.lineTop = l3;
    blockList.add(b5);

    mBlocks.addAll(blockList);

    updateLineLimit();
    Collections.sort(mBlocks, mBorderComparator);

    return blockList;
  }

  private void updateLineLimit() {
    for (Line line : mLines) {
      updateUpperLine(line);
      updateLowerLine(line);
    }
  }

  private void updateLowerLine(final Line line) {
    for (Line l : mLines) {
      if (l.getPosition() > line.getLowerLine().getPosition()
          && l.getPosition() < line.getPosition()
          && l.getDirection() == line.getDirection()) {

        if (l.getDirection() == Line.Direction.HORIZONTAL && (l.end.x <= line.start.x
            || l.start.x >= line.end.x)) {
          continue;
        }

        if (l.getDirection() == Line.Direction.VERTICAL && (l.end.y <= line.start.y
            || l.start.y >= line.end.y)) {
          continue;
        }

        line.setLowerLine(l);
      }
    }
  }

  private void updateUpperLine(final Line line) {
    for (Line l : mLines) {
      if (l.getPosition() < line.getUpperLine().getPosition()
          && l.getPosition() > line.getPosition()
          && l.getDirection() == line.getDirection()) {

        if (l.getDirection() == Line.Direction.HORIZONTAL && (l.end.x <= line.start.x
            || l.start.x >= line.end.x)) {
          continue;
        }

        if (l.getDirection() == Line.Direction.VERTICAL && (l.end.y <= line.start.y
            || l.start.y >= line.end.y)) {
          continue;
        }

        line.setUpperLine(l);
      }
    }
  }

  public void reset() {
    mLines.clear();
    mBlocks.clear();
    mBlocks.add(mOuterBlock);
  }

  public void update() {
    for (Line line : mLines) {
      line.update();
    }
  }

  public int getBlockSize() {
    return mBlocks.size();
  }

  public Block getBlock(int index) {
    return mBlocks.get(index);
  }

  public List<Line> getLines() {
    return mLines;
  }

  public List<Block> getBlocks() {
    return mBlocks;
  }

  public Block getOuterBlock() {
    return mOuterBlock;
  }

  public List<Line> getOuterLines() {
    return mOuterLines;
  }

  public int getTheme() {
    return mTheme;
  }
}
