package io.ruslan.top100crypto.job;

import io.ruslan.top100crypto.client.OrderDataClient;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

@Component
public class ConnectionJob {

  public static final String KRAKEN_WEB_SOCKET_URL = "wss://ws.kraken.com";
  public static final int DELAY_BETWEEN_READS = 2000;

  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 20)
  public void printKrakenMarketData()
      throws DeploymentException, IOException, InterruptedException {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    Session session = container.connectToServer(OrderDataClient.class,
        URI.create(KRAKEN_WEB_SOCKET_URL));
    Thread.sleep(DELAY_BETWEEN_READS);
    session.close();
  }
}
