package com.mbautista.tvmazemiddleware.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public record TvMazeSearchItemDto(Double score, TvMazeShowDto show) {

}
