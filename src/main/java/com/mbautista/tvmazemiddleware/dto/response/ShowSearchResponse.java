package com.mbautista.tvmazemiddleware.dto.response;

import java.util.List;

import lombok.Builder;

@Builder 
public record ShowSearchResponse(Integer id, 
                                 String name, String channel, 
                                 String summary, 
                                 List<String> genres

) {
}
