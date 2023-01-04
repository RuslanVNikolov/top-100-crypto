package io.ruslan.top100crypto.converter;

import io.ruslan.top100crypto.model.dto.UpdateMessageDto;
import org.json.JSONArray;
import org.json.JSONObject;

public class OrderBookConverter {

  public static final String ASKS_KEY = "a";
  public static final String BIDS_KEY = "b";

  public static UpdateMessageDto messageToDto(String message) {
    UpdateMessageDto dto = new UpdateMessageDto();
    JSONArray jsonArrayMessage = new JSONArray(message);
    JSONObject bidsAndAsks = jsonArrayMessage.getJSONObject(1);

    if (bidsAndAsks.has(ASKS_KEY)) {
      dto.setAsks(bidsAndAsks.getJSONArray(ASKS_KEY));
    }

    if (bidsAndAsks.has(BIDS_KEY)) {
      dto.setBids(bidsAndAsks.getJSONArray(BIDS_KEY));
    }

    dto.setAssetPair(jsonArrayMessage.getString(3));

    return dto;
  }

  public static String createBookSubscription(String assetPair) {
    JSONObject request = new JSONObject();
    JSONObject subscription = new JSONObject();
    JSONArray pairs = new JSONArray();

    subscription.put("name", "book");

    pairs.put(assetPair);

    request.put("event", "subscribe");
    request.put("pair", pairs);
    request.put("subscription", subscription);

    return request.toString();
  }
}
