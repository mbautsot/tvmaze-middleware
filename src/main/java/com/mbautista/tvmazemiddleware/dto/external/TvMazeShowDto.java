package com.mbautista.tvmazemiddleware.dto.external;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public record TvMazeShowDto(
    Integer id,
    String name,
    TvMazeChannelDto network,
    TvMazeChannelDto webChannel,
    String summary,
    List<String> genres
) {

    public String resolveChannelName() {
        if (network != null && network.name() != null && !network.name().isEmpty()) {
            return network.name();
        } else if (webChannel != null && webChannel.name() != null && !webChannel.name().isEmpty()) {
            return webChannel.name();
        } else {
            return "N/A";
        }
    }

}
