package com.example.onetouchapi.repository;

import com.example.onetouchapi.model.Shorter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShorterRepository extends JpaRepository<Shorter, Long> {
    boolean existsByShortUrl(String shortUrl);

    boolean existsByLongUrl(String longUrl);

    Shorter findShorterMappingsByShortUrl(String shortUrl);

    Shorter findShorterMappingsByLongUrl(String longUrl);

    List<Shorter> findAllByLongUrlLike(String longUrl);
}
