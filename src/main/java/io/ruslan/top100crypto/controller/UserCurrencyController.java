package io.ruslan.top100crypto.controller;

import io.ruslan.top100crypto.model.document.UserCurrency;
import io.ruslan.top100crypto.service.UserCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-currencies")
@Slf4j
public class UserCurrencyController {

    private final UserCurrencyService userCurrencyService;

    @PostMapping("/favorite")
    public ResponseEntity<UserCurrency> favorite(@RequestParam("cmcId") Long cmcId,
                                                 @RequestParam("favorite") Boolean favorite) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userCurrencyService.favorite(cmcId,
                favorite));
    }

    @GetMapping
    public ResponseEntity<List<UserCurrency>> getUserCurrencies() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userCurrencyService.getUserCurrencies());
    }
}
