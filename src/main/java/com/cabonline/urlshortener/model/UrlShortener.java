package com.cabonline.urlshortener.model;


import org.springframework.data.annotation.Id;

public record UrlShortener(@Id String id, String longUrl, String shortUrl) {
}
