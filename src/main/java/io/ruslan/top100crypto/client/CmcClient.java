package io.ruslan.top100crypto.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ruslan.top100crypto.model.dto.response.LatestResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CmcClient {

    public static final String API_KEY_HEADER = "X-CMC_PRO_API_KEY";
    private final ObjectMapper objectMapper;
    @Value("${cmc.api.key}")
    private String apiKey;

    public LatestResponseDto getLatest() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY_HEADER, apiKey);

        ResponseEntity<String> response = new RestTemplate().exchange("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest", HttpMethod.GET,
                new HttpEntity<>(headers), String.class);

        return objectMapper.readValue(response.getBody(), LatestResponseDto.class);
    }
}