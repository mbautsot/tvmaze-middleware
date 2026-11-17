package com.mbautista.tvmazemiddleware.repository;

import com.mbautista.tvmazemiddleware.model.ShowCacheDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShowCacheRepository extends MongoRepository<ShowCacheDocument, Integer> {

}
