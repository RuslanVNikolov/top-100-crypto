package io.ruslan.top100crypto.model.dto;

import lombok.*;
import org.json.JSONArray;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UpdateMessageDto {
  private JSONArray asks = new JSONArray();
  private JSONArray bids = new JSONArray();
  private String assetPair;
}
