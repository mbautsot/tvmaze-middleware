package com.mbautista.tvmazemiddleware.controller;

import com.mbautista.tvmazemiddleware.dto.request.CreateCommentRequest;
import com.mbautista.tvmazemiddleware.dto.response.ShowSearchResponse;
import com.mbautista.tvmazemiddleware.service.ShowService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
@Validated
public class ShowController {

    private final ShowService showService;

    @GetMapping("/search")
    public ResponseEntity<List<ShowSearchResponse>> searchShows(
            @RequestParam("search_query") @NotBlank(message = "search_query no puede estar vacío") String searchQuery) {
        return ResponseEntity.ok(showService.searchShows(searchQuery));
    }

    @GetMapping("/{show_id}")
    public ResponseEntity<?> getShowById(@PathVariable("show_id") Integer showId) {
        return ResponseEntity.ok(showService.getShowById(showId));
    }

    @PostMapping("/{show_id}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable("show_id") Integer showId,
            @Valid @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.addComment(showId, request));
    }
}