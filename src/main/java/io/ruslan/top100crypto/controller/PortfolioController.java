package io.ruslan.top100crypto.controller;

import io.ruslan.top100crypto.model.document.Portfolio;
import io.ruslan.top100crypto.model.dto.request.PortfolioRequest;
import io.ruslan.top100crypto.model.dto.request.TransactionRequest;
import io.ruslan.top100crypto.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public Portfolio getPortfolioByUserId() {
        return portfolioService.getUserPortfolio();
    }

    @PutMapping
    public Portfolio save(@RequestBody PortfolioRequest request) {
        return portfolioService.save(request);
    }

    @PostMapping("/transactions")
    public Portfolio saveTransaction(@RequestBody TransactionRequest request) {
        return portfolioService.save(request);
    }
}
