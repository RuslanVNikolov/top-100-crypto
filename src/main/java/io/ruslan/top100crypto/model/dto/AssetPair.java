package io.ruslan.top100crypto.model.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AssetPair {
    XBT_USD("XBT/USD"),
    ETH_USD("ETH/USD");

    private final String value;

    AssetPair(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

  public static List<String> getAllValues(){
    return Arrays.stream(AssetPair.values()).map(p -> p.getValue().toUpperCase()).collect(Collectors.toList());
  }
}
