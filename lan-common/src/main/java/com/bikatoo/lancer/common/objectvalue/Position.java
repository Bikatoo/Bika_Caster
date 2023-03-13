package com.bikatoo.lancer.common.objectvalue;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Position {

  // 精度
  private BigDecimal longitude;

  // 纬度
  private BigDecimal latitude;
}
