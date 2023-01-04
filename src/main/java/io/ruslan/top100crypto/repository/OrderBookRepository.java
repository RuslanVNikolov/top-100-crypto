package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.dto.AllOrderBookDataDto;
import io.ruslan.top100crypto.model.dto.AssetPair;
import io.ruslan.top100crypto.model.dto.OrderBookDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderBookRepository {

  private static final AllOrderBookDataDto data;

  static {
    data = new AllOrderBookDataDto();
    Map<String, OrderBookDto> dataMap = new HashMap<>();
    dataMap.put(AssetPair.XBT_USD.getValue(),
        new OrderBookDto(new ArrayList<>(), new ArrayList<>()));
    dataMap.put(AssetPair.ETH_USD.getValue(),
        new OrderBookDto(new ArrayList<>(), new ArrayList<>()));
    data.setData(dataMap);
  }

  public static OrderBookDto getOrderBookByAssetPair(String assetPair) {
    return data.getData().get(assetPair);
  }
}
