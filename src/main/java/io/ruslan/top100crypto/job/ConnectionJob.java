package io.ruslan.top100crypto.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ruslan.top100crypto.client.CmcClient;
import io.ruslan.top100crypto.converter.CurrencyMapper;
import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.model.dto.response.LatestResponseDto;
import io.ruslan.top100crypto.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConnectionJob {

  private final CmcClient client;
  private final CurrencyService currencyService;
  private final CurrencyMapper currencyMapper;

  @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 5)
  public void getLatest() throws JsonProcessingException {
    log.info("Getting the latest and greatest data");
    LatestResponseDto latest = client.getLatest();
    List<Currency> currencies = currencyMapper.dtoToDocuments(latest);
    currencyService.upsertAll(currencies);
  }
}
