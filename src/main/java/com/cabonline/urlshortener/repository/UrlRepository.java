package com.cabonline.urlshortener.repository;

import com.cabonline.urlshortener.model.UrlShortener;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<UrlShortener, String> {
}
