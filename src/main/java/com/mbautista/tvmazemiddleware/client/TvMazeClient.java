package com.mbautista.tvmazemiddleware.client;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.mbautista.tvmazemiddleware.dto.external.TvMazeSearchItemDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TvMazeClient {

    private final RestClient tvMazeRestClient;

    public List<TvMazeSearchItemDto> searchShows(String query) {
        List<TvMazeSearchItemDto> response = tvMazeRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/shows")
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return response != null ? response : Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> getShowById(Integer showId) {
        return tvMazeRestClient.get()
                .uri("/shows/{id}", showId)
                .retrieve()
                .body(Map.class);
    }
}
