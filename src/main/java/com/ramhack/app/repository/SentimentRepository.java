package com.ramhack.app.repository;

import com.ramhack.app.domain.Sentiment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sentiment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SentimentRepository extends JpaRepository<Sentiment, Long> {

}
