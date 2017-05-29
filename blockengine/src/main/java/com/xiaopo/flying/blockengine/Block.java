package com.xiaopo.flying.blockengine;

import android.graphics.PointF;
import android.graphics.RectF;
import java.util.Arrays;
import java.util.List;

/**
 * the block to layout puzzle piece
 *
 * each block consist of four lines : left,top,right,bottom
 *
 * @author wupanjie
 * @see Line
 * <p>
 */
public class Block {
  Line lineLeft;
  Line lineTop;
  Line lineRight;
  Line lineBottom;

  private float paddingLeft;
  private float paddingTop;
  private float paddingRight;
  private float paddingBottom;

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
    return right() - left();
  }

  public float height() {
    return bottom() - top();
  }

  public float left() {
    return lineLeft.start.x + paddingLeft;
  }

  public float top() {
    return lineTop.start.y + paddingTop;
  }

  public float right() {
    return lineRight.start.x - paddingRight;
  }

  public float bottom() {
    return lineBottom.start.y - paddingTop;
  }

  public float centerX() {
    return (right() + left()) * 0.5f;
  }

  public float centerY() {
    return (bottom() + top()) * 0.5f;
  }

  public float getPaddingLeft() {
    return paddingLeft;
  }

  public float getPaddingTop() {
    return paddingTop;
  }

  public float getPaddingRight() {
    return paddingRight;
  }

  public float getPaddingBottom() {
    return paddingBottom;
  }

  public void setPadding(float padding) {
    setPadding(padding, padding, padding, padding);
  }

  public void setPadding(float paddingLeft, float paddingTop, float paddingRight,
      float paddingBottom) {
    this.paddingLeft = paddingLeft;
    this.paddingTop = paddingTop;
    this.paddingRight = paddingRight;
    this.paddingBottom = paddingBottom;

    this.paddingLeft = paddingLeft;
    this.paddingTop = paddingTop;
    this.paddingRight = paddingRight;
    this.paddingBottom = paddingBottom;
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
