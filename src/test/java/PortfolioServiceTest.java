import io.ruslan.top100crypto.model.document.*;
import io.ruslan.top100crypto.model.dto.request.CurrencyRequest;
import io.ruslan.top100crypto.model.dto.request.PortfolioRequest;
import io.ruslan.top100crypto.model.dto.request.TransactionRequest;
import io.ruslan.top100crypto.model.dto.request.UserBalanceRequest;
import io.ruslan.top100crypto.model.dto.response.PortfolioResponseDto;
import io.ruslan.top100crypto.repository.CurrencyRepository;
import io.ruslan.top100crypto.repository.PortfolioRepository;
import io.ruslan.top100crypto.repository.TransactionRepository;
import io.ruslan.top100crypto.repository.UserBalanceRepository;
import io.ruslan.top100crypto.service.PortfolioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PortfolioServiceTest {

    @InjectMocks
    private PortfolioService portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserBalanceRepository userBalanceRepository;

    private User user;
    private Currency currency;
    private Transaction transaction;
    private UserBalance userBalance;
    private Portfolio portfolio;

    private PortfolioRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("123");
        currency = new Currency();
        currency.setId("currency1");
        currency.setName("Currency One");
        // Add a non-null value for valueUsd
        currency.setValueUsd(new BigDecimal("1000"));
        transaction = new Transaction();
        transaction.setId("transaction1");
        transaction.setAmount(new BigDecimal("1000"));
        transaction.setPriceUsd(new BigDecimal("0.01"));
        transaction.setDate(LocalDateTime.now());
        userBalance = new UserBalance();
        userBalance.setId("userbalance1");
        userBalance.setCurrency(currency);
        userBalance.setTransactions(Arrays.asList(transaction));
        portfolio = new Portfolio();
        portfolio.setId("portfolio1");
        portfolio.setName("My portfolio");
        portfolio.setUser(user);
        portfolio.setUserBalances(Arrays.asList(userBalance));

        // setup the request object
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(transaction.getAmount());
        transactionRequest.setPrice(transaction.getPriceUsd());
        transactionRequest.setDate(transaction.getDate());

        CurrencyRequest currencyRequest = new CurrencyRequest();
        currencyRequest.setId(currency.getId());
        currencyRequest.setName(currency.getName());

        UserBalanceRequest userBalanceRequest = new UserBalanceRequest();
        userBalanceRequest.setCurrency(currencyRequest);
        userBalanceRequest.setTransactions(Arrays.asList(transactionRequest));

        request = new PortfolioRequest();
        request.setName(portfolio.getName());
        request.setUserBalances(List.of(userBalanceRequest));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void testGetUserPortfolio() {
        when(portfolioRepository.findAllByUserId(user.getId())).thenReturn(Arrays.asList(portfolio));

        PortfolioResponseDto result = portfolioService.getUserPortfolio();

        verify(portfolioRepository, times(1)).findAllByUserId(user.getId());
        assertEquals(portfolio.getName(), result.getName());
    }

    @Test
    public void testSavePortfolio() {
        when(userBalanceRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(portfolioRepository.save(any())).thenReturn(portfolio);

        Portfolio result = portfolioService.save(request);

        verify(userBalanceRepository, times(1)).saveAll(any());
        assertEquals(portfolio.getId(), result.getId());
    }
}
