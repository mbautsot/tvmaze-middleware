package com.mbautista.tvmazemiddleware.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mbautista.tvmazemiddleware.model.ShowCommentDocument;

public interface ShowCommentRepository extends MongoRepository<ShowCommentDocument, String> {
    List<ShowCommentDocument> findByShowIdOrderByCreatedAtDesc(Integer showId);
}
