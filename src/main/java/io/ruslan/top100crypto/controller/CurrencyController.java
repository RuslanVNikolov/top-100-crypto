package io.ruslan.top100crypto.controller;

import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.model.document.CurrencyHistory;
import io.ruslan.top100crypto.service.CurrencyService;
import io.ruslan.top100crypto.service.UserCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/currencies")
@Slf4j
public class CurrencyController {

    private final CurrencyService currencyService;
    private final UserCurrencyService userCurrencyService;

    @GetMapping("/top100")
    public ResponseEntity<List<Currency>> getTop100Currencies() {
        List<Currency> response = currencyService.getTop100Currencies();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<CurrencyHistory>> getHistoryByCmcId(@RequestParam("cmcId") Long cmcId,
                                                                   @RequestParam(value = "from", required = false)
                                                                   Optional<String> fromStr,
                                                                   @RequestParam(value = "to", required = false)
                                                                   Optional<String> toStr) {
        Instant from =
                fromStr.map(Instant::parse).orElse(ZonedDateTime.now(ZoneId.systemDefault()).minusYears(5).toInstant());
        Instant to = toStr.map(Instant::parse).orElse(Instant.now());
        List<CurrencyHistory> response = currencyService.getHistoryForCurrency(cmcId, from, to);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
