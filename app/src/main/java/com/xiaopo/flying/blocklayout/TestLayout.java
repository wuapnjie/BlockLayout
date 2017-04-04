package com.xiaopo.flying.blocklayout;

import com.xiaopo.flying.blockengine.Line;
import com.xiaopo.flying.blockengine.PuzzleLayout;

/**
 * @author wupanjie
 */

public class TestLayout extends PuzzleLayout {
  @Override public void layout() {
    addLine(getBlock(0), Line.Direction.HORIZONTAL, 0.5f);
    addLine(getBlock(1), Line.Direction.VERTICAL, 0.5f);
  }
}
