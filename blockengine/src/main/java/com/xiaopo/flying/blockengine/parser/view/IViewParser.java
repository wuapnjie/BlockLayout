package com.xiaopo.flying.blockengine.parser.view;

import android.content.Context;
import android.view.View;

/**
 * @author wupanjie
 */

public interface IViewParser<T extends View> {
  T parseView(Context context,String info);

  boolean canParseIt(String info);
}
