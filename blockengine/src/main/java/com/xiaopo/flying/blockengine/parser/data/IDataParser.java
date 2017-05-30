package com.xiaopo.flying.blockengine.parser.data;

/**
 * @author wupanjie
 */

public interface IDataParser<T> {
  T parserData(String info);
}
