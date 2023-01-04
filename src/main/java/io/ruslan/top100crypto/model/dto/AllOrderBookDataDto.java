package io.ruslan.top100crypto.model.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AllOrderBookDataDto {
  private Map<String, OrderBookDto> data = new HashMap<>();
}