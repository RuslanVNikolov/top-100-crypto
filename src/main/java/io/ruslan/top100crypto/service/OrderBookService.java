package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.converter.OrderBookConverter;
import io.ruslan.top100crypto.facade.OrderBookFacade;
import io.ruslan.top100crypto.model.dto.AssetPair;
import io.ruslan.top100crypto.model.dto.UpdateMessageDto;
import io.ruslan.top100crypto.repository.OrderBookRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static io.ruslan.top100crypto.converter.OrderBookConverter.ASKS_KEY;
import static io.ruslan.top100crypto.converter.OrderBookConverter.BIDS_KEY;

public class OrderBookService {

  public static final int MAX_DATA_ENTRIES = 10;

  public static void publishUpdate(String updateMessage) {
    if (validateMessage(updateMessage)) {
      processMarketData(OrderBookConverter.messageToDto(updateMessage));
    }
  }

  private static boolean validateMessage(String message) {
    if (!message.startsWith("[")) {
      return false;
    }
    JSONArray jsonArrayMessage = new JSONArray(message);

    if (!AssetPair.getAllValues().contains(jsonArrayMessage.getString(3).toUpperCase())) {
      return false;
    }

    try {
      JSONObject bidsAndAsks = jsonArrayMessage.getJSONObject(1);
      return bidsAndAsks.has(ASKS_KEY) || bidsAndAsks.has(BIDS_KEY);
    } catch (JSONException e) {
      return false;
    }
  }

  private static void processMarketData(UpdateMessageDto dto) {
    storeData(dto.getAsks(), OrderBookRepository.getOrderBookByAssetPair(dto.getAssetPair())
        .getAsks());
    storeData(dto.getBids(), OrderBookRepository.getOrderBookByAssetPair(dto.getAssetPair())
        .getBids());
    OrderBookFacade.printOrderBookForAssetPair(dto.getAssetPair(),
        OrderBookRepository.getOrderBookByAssetPair(dto.getAssetPair()));
  }

  private static void storeData(JSONArray entries,
      List<Pair<BigDecimal, BigDecimal>> storedData) {
    for (int i = 0; i < entries.length(); i++) {
      Pair<BigDecimal, BigDecimal> currentBid =
          Pair.of(new BigDecimal(entries.getJSONArray(i).getString(0)).stripTrailingZeros(),
              new BigDecimal(entries.getJSONArray(i).getString(1)).stripTrailingZeros());
      storedData.add(currentBid);
      storedData.removeIf(pair -> pair.getSecond().compareTo(BigDecimal.ZERO) <= 0);
      storedData.sort(Comparator.comparing(Pair::getFirst));

      if (storedData.size() > MAX_DATA_ENTRIES) {
        storedData.remove(MAX_DATA_ENTRIES);
      }
    }
  }
}
