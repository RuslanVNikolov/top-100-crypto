package io.ruslan.top100crypto.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyHistoryRequest {
    private String cmcId;
    private LocalDateTime from = LocalDateTime.MIN;
    private LocalDateTime to = LocalDateTime.now();
}
