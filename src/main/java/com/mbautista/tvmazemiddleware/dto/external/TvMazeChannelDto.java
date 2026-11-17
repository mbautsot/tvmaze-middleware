package com.mbautista.tvmazemiddleware.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvMazeChannelDto(
        Integer id,
        String name
) {

}
