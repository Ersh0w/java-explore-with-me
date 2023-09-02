package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, boolean unique) {
        if (uris != null) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("start", start);
            parameters.put("end", end);
            for (String s : uris) {
                parameters.put("uris", s);
            }
            parameters.put("unique", unique);

            return get("/stats/?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        } else {
            Map<String, Object> parameters = Map.of("start", start,
                    "end", end,
                    "unique", unique
            );

            return get("/stats/?start={start}&end={end}&unique={unique}", parameters);
        }
    }

    public ResponseEntity<Object> add(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }
}
