package com.xiaopo.flying.blockengine;

import android.graphics.PointF;
import android.graphics.RectF;
import java.util.Arrays;
import java.util.List;

/**
 * the block to layout puzzle piece
 *
 * each block consist of four lines : left,top,right,bottom
 * @see Line
 * <p>
 * @author wupanjie
 */
public class Block {
  Line lineLeft;
  Line lineTop;
  Line lineRight;
  Line lineBottom;

  public Block(Block src) {
    lineLeft = src.lineLeft;
    lineTop = src.lineTop;
    lineRight = src.lineRight;
    lineBottom = src.lineBottom;
  }

  public Block(RectF baseRect) {
    setBaseRect(baseRect);
  }

  private void setBaseRect(RectF baseRect) {
    PointF one = new PointF(baseRect.left, baseRect.top);
    PointF two = new PointF(baseRect.right, baseRect.top);
    PointF three = new PointF(baseRect.left, baseRect.bottom);
    PointF four = new PointF(baseRect.right, baseRect.bottom);

    lineLeft = new Line(one, three);
    lineTop = new Line(one, two);
    lineRight = new Line(two, four);
    lineBottom = new Line(three, four);
  }

  public float width() {
    return lineRight.start.x - lineLeft.start.x;
  }

  public float height() {
    return lineBottom.start.y - lineTop.start.y;
  }

  public float left() {
    return lineLeft.start.x;
  }

  public float top() {
    return lineTop.start.y;
  }

  public float right() {
    return lineRight.start.x;
  }

  public float bottom() {
    return lineBottom.start.y;
  }

  public float centerX() {
    return (right() + left()) * 0.5f;
  }

  public float centerY() {
    return (bottom() + top()) * 0.5f;
  }

  public List<Line> getLines() {
    return Arrays.asList(lineLeft, lineTop, lineRight, lineBottom);
  }

  public RectF getRect() {
    return new RectF(left(), top(), right(), bottom());
  }

  public boolean contains(Line line) {
    return lineLeft == line || lineTop == line || lineRight == line || lineBottom == line;
  }

  @Override public String toString() {
    return "left line:\n"
        + lineLeft.toString()
        + "\ntop line:\n"
        + lineTop.toString()
        + "\nright line:\n"
        + lineRight.toString()
        + "\nbottom line:\n"
        + lineBottom.toString()
        + "\nthe rect is \n"
        + getRect().toString();  //TODO
  }
}
