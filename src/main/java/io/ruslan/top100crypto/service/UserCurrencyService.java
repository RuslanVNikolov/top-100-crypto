package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.model.document.User;
import io.ruslan.top100crypto.model.document.UserCurrency;
import io.ruslan.top100crypto.repository.CurrencyRepository;
import io.ruslan.top100crypto.repository.UserCurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserCurrencyService {

    private final CurrencyRepository currencyRepository;
    private final UserCurrencyRepository userCurrencyRepository;

    @SneakyThrows
    public UserCurrency favorite(Long cmcId, Boolean favorite) {
        Currency currency = currencyRepository.findByCmcId(cmcId).orElseThrow(() -> new Exception("Currency not " +
                "found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        UserCurrency userCurrency =
                userCurrencyRepository.findByUserIdAndCurrencyId(user.getId(), currency.getId()).orElse(UserCurrency.builder().currency(currency).user(user).build());
        userCurrency.setFavorite(favorite);
        return userCurrencyRepository.save(userCurrency);
    }

    @SneakyThrows
    public List<UserCurrency> getUserCurrencies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userCurrencyRepository.findAllByUserId(user.getId());
    }
}
