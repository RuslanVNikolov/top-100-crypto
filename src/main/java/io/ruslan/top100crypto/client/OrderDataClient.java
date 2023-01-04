package io.ruslan.top100crypto.client;

import io.ruslan.top100crypto.converter.OrderBookConverter;
import io.ruslan.top100crypto.model.dto.AssetPair;
import io.ruslan.top100crypto.service.OrderBookService;
import jakarta.websocket.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ClientEndpoint
public class OrderDataClient {

    @OnOpen
    public void onOpen(Session session) {
        try {
            session.getBasicRemote()
                    .sendText(OrderBookConverter.createBookSubscription(AssetPair.XBT_USD.getValue()));
            session.getBasicRemote()
                    .sendText(OrderBookConverter.createBookSubscription(AssetPair.ETH_USD.getValue()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void processMessage(String message) {
        OrderBookService.publishUpdate(message);
    }

    @OnError
    public void processError(Throwable t) {
        System.out.println(t.getMessage());
    }
}