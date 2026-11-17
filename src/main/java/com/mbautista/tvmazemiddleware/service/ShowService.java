package com.mbautista.tvmazemiddleware.service;

import com.mbautista.tvmazemiddleware.client.TvMazeClient;
import com.mbautista.tvmazemiddleware.dto.external.TvMazeShowDto;
import com.mbautista.tvmazemiddleware.dto.response.ShowSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final TvMazeClient tvMazeClient;

    public List<ShowSearchResponse> searchShows(String searchQuery) {
        return tvMazeClient.searchShows(searchQuery).stream()
                .filter(Objects::nonNull)
                .map(item -> item.show())
                .filter(Objects::nonNull)
                .map(this::mapToSearchResponse)
                .toList();
    }

    public Map<String, Object> getShowById(Integer showId) {
        return tvMazeClient.getShowById(showId);
    }

    private ShowSearchResponse mapToSearchResponse(TvMazeShowDto show) {
        return ShowSearchResponse.builder()
                .id(show.id())
                .name(show.name())
                .channel(show.resolveChannelName())
                .summary(show.summary())
                .genres(show.genres())
                .build();
    }
}