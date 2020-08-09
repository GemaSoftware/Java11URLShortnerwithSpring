package com.agrongemajli.urlShortener.controllers;

import com.agrongemajli.urlShortener.entities.URLShort;
import com.agrongemajli.urlShortener.repos.ShortURLRepository;
import com.agrongemajli.urlShortener.utilities.URLUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URL;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final ShortURLRepository shortURLRepository;

    @Autowired
    public ApiController(ShortURLRepository shortURLRepository) {
        this.shortURLRepository = shortURLRepository;
    }

    // Receives Short URL Object via slug given. returns in JSON.
    @GetMapping(path = "/shorturl/{shortSlug}")
    public ResponseEntity<URLShort> redirectSlug(@PathVariable String shortSlug){
        Optional<URLShort> shortOptional = shortURLRepository.findByShortSlug(shortSlug);
        return shortOptional.map(urlShort -> ResponseEntity.ok().body(urlShort)).orElse(ResponseEntity.badRequest().build());
    }

    //Creates short url based on URL passed as JSON.
    //Creates random alphanumeric and checks if exists in db.
    //Allows same url to be added in db.
    @PostMapping(path = "/shorturl/create", consumes = "application/json")
    public ResponseEntity<URLShort> createSlug(@RequestBody URLShort urlShort){
        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        if(urlValidator.isValid(urlShort.getFullURL())){
            String slugID = RandomStringUtils.randomAlphanumeric(10);
            while(shortURLRepository.findByShortSlug(slugID).isPresent()){
                RandomStringUtils.randomAlphanumeric(10);
            }
            urlShort.setShortSlug(slugID);
            return ResponseEntity.ok().body(shortURLRepository.save(urlShort));
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
