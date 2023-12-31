package com.cabonline.urlshortener.controller;

import com.cabonline.urlshortener.model.UrlShortener;
import com.cabonline.urlshortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/url-shortener/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable String shortUrl) {
        Optional<UrlShortener> urlShortener = urlService.redirect(shortUrl);

        if (urlShortener.isPresent()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            trySetLocation(httpHeaders, urlShortener.get());
            return new ResponseEntity<>(urlShortener.get().shortUrl(), httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/url-shortener")
    public ResponseEntity<?> addUrl(@RequestParam String longUrl) {
        UrlShortener urlShortener = urlService.createShortUrl(longUrl);
        return new ResponseEntity<>(urlShortener, HttpStatus.CREATED);
    }

    private static void trySetLocation(HttpHeaders httpHeaders, UrlShortener urlShortener) {
        try {
            httpHeaders.setLocation(new URI(urlShortener.longUrl()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
