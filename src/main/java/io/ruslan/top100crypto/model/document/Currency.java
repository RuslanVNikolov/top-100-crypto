package io.ruslan.top100crypto.model.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "currency")
public class Currency {

    @Id
    private String id;
    private Long cmcId;

    private String name;
    private String shortName;
    @DBRef
    private Avatar avatar;
    private BigDecimal valueUsd;
    private BigDecimal marketCap;
    private BigDecimal change24h;
}
