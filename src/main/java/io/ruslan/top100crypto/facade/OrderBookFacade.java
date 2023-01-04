package io.ruslan.top100crypto.facade;

import io.ruslan.top100crypto.model.dto.OrderBookDto;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderBookFacade {

  public static void printOrderBookForAssetPair(String assetPair, OrderBookDto dto) {
    List<Pair<BigDecimal, BigDecimal>> asks = dto.getAsks();
    List<Pair<BigDecimal, BigDecimal>> bids = dto.getBids();

    System.out.println("<------------------------------------>");

    if (!asks.isEmpty()) {
      System.out.println("asks:");
      asks.forEach(System.out::println);
      System.out.print("best ask:");
      System.out.println(asks.get(0));
    } else {
      System.out.println("asks N/A");
    }

    if (!bids.isEmpty()) {
      System.out.print("best bid:");
      System.out.println(bids.get(bids.size() - 1));
      System.out.println("bids:");
      bids.forEach(System.out::println);
    } else {
      System.out.println("bids N/A");
    }

    System.out.println(Instant.now());
    System.out.println(assetPair);
    System.out.println(">------------------------------------<");
  }
}
