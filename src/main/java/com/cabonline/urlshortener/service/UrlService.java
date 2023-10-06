package com.cabonline.urlshortener.service;

import com.cabonline.urlshortener.model.UrlShortener;
import com.cabonline.urlshortener.repository.UrlRepository;
import io.seruco.encoding.base62.Base62;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {

    private static final String LOCAL_HOST_APT_PATH = "http://localhost:8080/url-shortener/";

    private final MongoTemplate mongoTemplate;
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository, MongoTemplate mongoTemplate) {
        this.urlRepository = urlRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<UrlShortener> redirect(String shortUrl) {

        Query query = new Query();
        query.addCriteria(Criteria.where("shortUrl").is(LOCAL_HOST_APT_PATH + shortUrl));
        return Optional.ofNullable(mongoTemplate.findOne(query, UrlShortener.class));
    }

    public UrlShortener createShortUrl(String longUrl) {
        String shortUrl = createShortUrlFromLongUrl(longUrl);
        UrlShortener urlShortener = new UrlShortener(longUrl, LOCAL_HOST_APT_PATH + shortUrl);

        Query query = new Query();
        query.addCriteria(Criteria.where("longUrl").is(longUrl));
        Optional<UrlShortener> urlShortenerOptional = Optional.ofNullable(mongoTemplate.findOne(query, UrlShortener.class));

        return urlShortenerOptional.orElseGet(() -> urlRepository.save(urlShortener));
    }

    private static String createShortUrlFromLongUrl(String longUrl) {
        String cleanedUrl = cleanUrl(longUrl);
        Base62 base62 = Base62.createInstance();
        return new String(base62.encode(cleanedUrl.getBytes())).substring(0, 6);
    }

    private static String cleanUrl(String url) {
        return url.replaceAll("\\+", "")
                .replaceAll("/", "")
                .replaceAll("=", "")
                .replaceAll(":", "")
                .replaceAll("\\.", "")
                .replaceAll("-", "");
    }
}
