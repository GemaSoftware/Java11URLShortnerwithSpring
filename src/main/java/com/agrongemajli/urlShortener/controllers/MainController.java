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
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {

    private final ShortURLRepository shortURLRepository;

    @Autowired
    public MainController(ShortURLRepository shortURLRepository) {
        this.shortURLRepository = shortURLRepository;
    }


    // Redirects to URL held by slug in database if exists.
    @GetMapping(path = "/{shortSlug}")
    public String redirectSlug(@PathVariable String shortSlug){
        Optional<URLShort> shortOptional = shortURLRepository.findByShortSlug(shortSlug);
        return shortOptional.map(urlShort -> "redirect:" + urlShort.getFullURL()).orElse("404");
    }


    //Creates short url based on URL passed as JSON.
    //Creates random alphanumeric and checks if exists in db.
    //Allows same url to be added in db.
    @PostMapping(path = "/createshort", consumes = "application/json")
    public ResponseEntity<URLShort> createSlug(@RequestBody URLShort urlShort){
        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        if(urlValidator.isValid(urlShort.getFullURL())){
            String slugID = RandomStringUtils.randomAlphanumeric(8);
            while(shortURLRepository.findByShortSlug(slugID).isPresent()){
                RandomStringUtils.randomAlphanumeric(8);
            }
            urlShort.setShortSlug(slugID);
            return ResponseEntity.ok().body(shortURLRepository.save(urlShort));
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
