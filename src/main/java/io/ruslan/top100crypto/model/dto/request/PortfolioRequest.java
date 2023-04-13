package io.ruslan.top100crypto.model.dto.request;

import io.ruslan.top100crypto.model.document.UserBalance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioRequest {
    private String id;
    private String username;
    private String name;
    private List<UserBalanceRequest> userBalances;
}
