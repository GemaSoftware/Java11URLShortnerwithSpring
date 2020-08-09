package com.agrongemajli.urlShortener.repos;

import com.agrongemajli.urlShortener.entities.URLShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortURLRepository extends CrudRepository<URLShort, Long> {
        Optional<URLShort> findByShortSlug(String shortSlug);
}
