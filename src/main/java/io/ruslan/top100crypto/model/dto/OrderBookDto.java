package io.ruslan.top100crypto.model.dto;

import lombok.*;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class OrderBookDto {
  private List<Pair<BigDecimal, BigDecimal>> asks = new ArrayList<>();
  private List<Pair<BigDecimal, BigDecimal>> bids = new ArrayList<>();
}
