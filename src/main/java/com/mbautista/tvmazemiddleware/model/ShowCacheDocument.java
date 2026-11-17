package com.mbautista.tvmazemiddleware.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@Document(collection = "shows")
public class ShowCacheDocument {

    @Id 
    private Integer id;
    private Map<String, Object> showData;
    private LocalDateTime cachedAt;

}
