package io.ruslan.top100crypto.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user_currency")
public class UserCurrency {
    @Id
    private String id;
    @DBRef
    private Currency currency;
    @DBRef
    private User user;
    private Boolean favorite;
}
