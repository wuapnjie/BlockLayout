package com.xiaopo.flying.blockengine;

import com.xiaopo.flying.blockengine.parser.data.IDataParser;
import com.xiaopo.flying.blockengine.parser.view.IViewParser;

/**
 * @author wupanjie
 */

public interface Engine {
  void registerViewParser(IViewParser<?> viewParser);

  void registerDataParser(IDataParser<?> dataParser);
}
