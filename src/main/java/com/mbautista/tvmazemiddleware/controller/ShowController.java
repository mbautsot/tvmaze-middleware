package com.mbautista.tvmazemiddleware.controller;

import com.mbautista.tvmazemiddleware.dto.response.ShowSearchResponse;
import com.mbautista.tvmazemiddleware.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
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
}