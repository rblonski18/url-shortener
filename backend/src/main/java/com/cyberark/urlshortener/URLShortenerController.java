package com.cyberark.urlshortener;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // allow cors from port 3000 for REST requests
@RequestMapping(value = "/url")
public class URLShortenerController {
 
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
 
    @Value("${redis.ttl}")
    private long ttl;
 
    @CrossOrigin(origins = "http://localhost:3000") // allow cors from port 3000 for REST requests
    @PostMapping
    public ResponseEntity create(@RequestBody final String url) {
    	
        // validate url
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        
        if (!urlValidator.isValid(url)) {
        	
            // Invalid url return HTTP 400 bad request.
            return ResponseEntity.badRequest().body(new Error("Invalid URL."));
            
        }
 
        // hash url to id
        final String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
        
        // store url in redis with key=id
        redisTemplate.opsForValue().set(id, url, ttl, TimeUnit.SECONDS);
        
        // return id. 
        return ResponseEntity.ok(id);
        
    }
 
    // path = http://{host}/url/{id}
    @CrossOrigin(origins = "http://localhost:3000") // allow cors from port 3000 for REST requests
    @GetMapping(value = "/{id}")
    public ResponseEntity getUrl(@PathVariable final String id) {
    	
        // get url associated with input id from redis
        final String url = redisTemplate.opsForValue().get(id);
        
        if (Objects.isNull(url)) {
        	// if nothing found associated to input id, return 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("No key exists."));
        } 
 
        // otherwise, return url 
        return ResponseEntity.ok(url);
    }
}