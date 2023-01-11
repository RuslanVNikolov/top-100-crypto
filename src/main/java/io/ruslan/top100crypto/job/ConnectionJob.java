package io.ruslan.top100crypto.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ruslan.top100crypto.client.CmcClient;
import io.ruslan.top100crypto.converter.CurrencyMapper;
import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.model.dto.response.LatestResponseDto;
import io.ruslan.top100crypto.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ConnectionJob {

  private final CmcClient client;
  private final CurrencyService currencyService;
  private final CurrencyMapper currencyMapper;

  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 20)
  public void getLatest() throws JsonProcessingException {
    LatestResponseDto latest = client.getLatest();
    List<Currency> currencies = currencyMapper.dtoToDocuments(latest);
    currencyService.upsertAll(currencies);
  }
}
