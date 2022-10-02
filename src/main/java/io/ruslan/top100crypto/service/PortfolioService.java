package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.Portfolio;
import io.ruslan.top100crypto.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public Portfolio getPortfolioById(String id) {
        return portfolioRepository.findByIdAndUserId(id,"figure out how to get it from session data");
    }
}
