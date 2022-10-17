package io.ruslan.top100crypto.controller;

import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/top100")
    public List<Currency> getTop100Currencies() {
        return currencyService.getTop100Currencies();
    }
}
