package io.ruslan.top100crypto.controller;

import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/currencies")
@Slf4j
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/top100")
    public ResponseEntity<List<Currency>> getTop100Currencies() {
        List<Currency> response = currencyService.getTop100Currencies();
        log.info("top 100 response: " + response.toString());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
