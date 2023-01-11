package io.ruslan.top100crypto.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoResponseDto {
    private Long id;
    private String name;
    private String symbol;
    private QuoteResponseDto quote;
}
