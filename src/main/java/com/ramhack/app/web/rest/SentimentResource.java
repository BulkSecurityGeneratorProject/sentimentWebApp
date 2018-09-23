package com.ramhack.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ramhack.app.domain.Sentiment;
import com.ramhack.app.repository.SentimentRepository;
import com.ramhack.app.web.rest.errors.BadRequestAlertException;
import com.ramhack.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sentiment.
 */
@RestController
@RequestMapping("/api")
public class SentimentResource {

    private final Logger log = LoggerFactory.getLogger(SentimentResource.class);

    private static final String ENTITY_NAME = "sentiment";

    private final SentimentRepository sentimentRepository;

    public SentimentResource(SentimentRepository sentimentRepository) {
        this.sentimentRepository = sentimentRepository;
    }

    /**
     * POST  /sentiments : Create a new sentiment.
     *
     * @param sentiment the sentiment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sentiment, or with status 400 (Bad Request) if the sentiment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sentiments")
    @Timed
    public ResponseEntity<Sentiment> createSentiment(@Valid @RequestBody Sentiment sentiment) throws URISyntaxException {
        log.debug("REST request to save Sentiment : {}", sentiment);
        if (sentiment.getId() != null) {
            throw new BadRequestAlertException("A new sentiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sentiment result = sentimentRepository.save(sentiment);
        return ResponseEntity.created(new URI("/api/sentiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sentiments : Updates an existing sentiment.
     *
     * @param sentiment the sentiment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sentiment,
     * or with status 400 (Bad Request) if the sentiment is not valid,
     * or with status 500 (Internal Server Error) if the sentiment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sentiments")
    @Timed
    public ResponseEntity<Sentiment> updateSentiment(@Valid @RequestBody Sentiment sentiment) throws URISyntaxException {
        log.debug("REST request to update Sentiment : {}", sentiment);
        if (sentiment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sentiment result = sentimentRepository.save(sentiment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sentiment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sentiments : get all the sentiments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sentiments in body
     */
    @GetMapping("/sentiments")
    @Timed
    public List<Sentiment> getAllSentiments() {
        log.debug("REST request to get all Sentiments");
        return sentimentRepository.findAll();
    }

    /**
     * GET  /sentiments/:id : get the "id" sentiment.
     *
     * @param id the id of the sentiment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sentiment, or with status 404 (Not Found)
     */
    @GetMapping("/sentiments/{id}")
    @Timed
    public ResponseEntity<Sentiment> getSentiment(@PathVariable Long id) {
        log.debug("REST request to get Sentiment : {}", id);
        Optional<Sentiment> sentiment = sentimentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sentiment);
    }

    /**
     * DELETE  /sentiments/:id : delete the "id" sentiment.
     *
     * @param id the id of the sentiment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sentiments/{id}")
    @Timed
    public ResponseEntity<Void> deleteSentiment(@PathVariable Long id) {
        log.debug("REST request to delete Sentiment : {}", id);

        sentimentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
