package com.xiaopo.flying.blocklayout;

import android.text.TextUtils;
import com.xiaopo.flying.blockengine.Line;
import com.xiaopo.flying.blockengine.PuzzleLayout;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author wupanjie
 */

public class JsonTestLayout extends PuzzleLayout {
  private final String layoutJsonString;

  public JsonTestLayout(String layoutJsonString) {
    this.layoutJsonString = layoutJsonString;
  }

  @Override
  public void layout() {
    try {
      JSONObject layoutJson = new JSONObject(layoutJsonString);

      JSONArray steps = layoutJson.getJSONArray("steps");

      for (int i = 0; i < steps.length(); i++) {
        JSONObject step = steps.optJSONObject(i);
        String method = step.optString("method");
        int position = step.optInt("position");
        if (TextUtils.equals(method, "addLine")) {
          int direction = step.optInt("direction");
          double radio = step.optDouble("radio");
          addLine(getBlock(position), Line.Direction.get(direction), (float) radio);
        } else if (TextUtils.equals(method, "addCross")) {
          double hRadio = step.optDouble("hRadio");
          double vRadio = step.optDouble("vRadio");
          addCross(getBlock(position), (float) hRadio, (float) vRadio);
        } else if (TextUtils.equals(method, "cutEqual1")) {
          int direction = step.optInt("direction");
          int part = step.optInt("part");
          cutBlockEqualPart(getBlock(position), part, Line.Direction.get(direction));
        } else if (TextUtils.equals(method, "cutEqual2")) {
          int hSize = step.optInt("hSize");
          int vSize = step.optInt("vSize");
          cutBlockEqualPart(getBlock(position), hSize, vSize);
        } else if (TextUtils.equals(method, "cutSpiral")) {
          cutSpiral(getBlock(position));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
