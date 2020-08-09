package com.agrongemajli.urlShortener.entities;


import lombok.Data;

import javax.persistence.*;
import java.net.URL;

@Entity
@Data
public class URLShort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long urlShortID;

    @Column(unique = true)
    private String shortSlug;

    @Column
    private String fullURL;

    public URLShort(){}

    public URLShort(String slug, String url){
        this.shortSlug = slug;
        this.fullURL = url;
    }

    @Override
    public String toString() {
        return "URLShort{" +
                "urlShortID=" + urlShortID +
                ", shortSlug='" + shortSlug + '\'' +
                ", fullURL='" + fullURL + '\'' +
                '}';
    }


}
