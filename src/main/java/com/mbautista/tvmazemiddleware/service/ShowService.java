package com.mbautista.tvmazemiddleware.service;

import com.mbautista.tvmazemiddleware.client.TvMazeClient;
import com.mbautista.tvmazemiddleware.dto.external.TvMazeShowDto;
import com.mbautista.tvmazemiddleware.dto.request.CreateCommentRequest;
import com.mbautista.tvmazemiddleware.dto.response.ShowSearchResponse;
import com.mbautista.tvmazemiddleware.model.ShowCacheDocument;
import com.mbautista.tvmazemiddleware.model.ShowCommentDocument;
import com.mbautista.tvmazemiddleware.repository.ShowCacheRepository;
import com.mbautista.tvmazemiddleware.repository.ShowCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowService {

    private final TvMazeClient tvMazeClient;
    private final ShowCacheRepository showCacheRepository;
    private final ShowCommentRepository showCommentRepository;

    public List<ShowSearchResponse> searchShows(String searchQuery) {
        return tvMazeClient.searchShows(searchQuery).stream()
                .filter(Objects::nonNull)
                .map(item -> item.show())
                .filter(Objects::nonNull)
                .map(this::mapToSearchResponse)
                .toList();
    }

    public Map<String, Object> getShowById(Integer showId) {
        Map<String, Object> baseShow = showCacheRepository.findById(showId)
                .map(cached -> {
                    log.info("Show {} obtenido desde caché de MongoDB", showId);
                    return cached.getShowData();
                })
                .orElseGet(() -> fetchAndCacheShow(showId));

        List<ShowCommentDocument> comments = showCommentRepository.findByShowIdOrderByCreatedAtDesc(showId);
        double averageRating = comments.stream()
                .mapToDouble(c -> c.getRating())                
                .average()
                .orElse(0.0);

        Map<String, Object> enrichedShow = new LinkedHashMap<>(baseShow);
        enrichedShow.put("averageUserRating", Math.round(averageRating * 10.0) / 10.0);
        enrichedShow.put("comments", comments);
        return enrichedShow;
    }

    public ShowCommentDocument addComment(Integer showId, CreateCommentRequest request) {
        if (!showCacheRepository.existsById(showId)) {
            fetchAndCacheShow(showId);
        }

        ShowCommentDocument comment = ShowCommentDocument.builder()
                .showId(showId)
                .author(request.author().trim())
                .comment(request.comment().trim())
                .rating(request.rating())
                .createdAt(LocalDateTime.now())
                .build();

        return showCommentRepository.save(comment);
    }

    private Map<String, Object> fetchAndCacheShow(Integer showId) {
        log.info("Show {} no encontrado en caché. Consultando API TV Maze...", showId);
        Map<String, Object> externalShow = tvMazeClient.getShowById(showId);

        if (externalShow != null) {
            ShowCacheDocument cacheDocument = ShowCacheDocument.builder()
                    .id(showId)
                    .showData(externalShow)
                    .cachedAt(LocalDateTime.now())
                    .build();
            showCacheRepository.save(cacheDocument);
        }

        return externalShow;
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