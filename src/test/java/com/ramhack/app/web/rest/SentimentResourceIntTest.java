package com.ramhack.app.web.rest;

import com.ramhack.app.SentimentWebApp;

import com.ramhack.app.domain.Sentiment;
import com.ramhack.app.repository.SentimentRepository;
import com.ramhack.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.ramhack.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SentimentResource REST controller.
 *
 * @see SentimentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SentimentWebApp.class)
public class SentimentResourceIntTest {

    private static final String DEFAULT_KEYWORD = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORD = "BBBBBBBBBB";

    private static final String DEFAULT_SENTIMENT = "AAAAAAAAAA";
    private static final String UPDATED_SENTIMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PLATFORM = "AAAAAAAAAA";
    private static final String UPDATED_PLATFORM = "BBBBBBBBBB";

    @Autowired
    private SentimentRepository sentimentRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSentimentMockMvc;

    private Sentiment sentiment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SentimentResource sentimentResource = new SentimentResource(sentimentRepository);
        this.restSentimentMockMvc = MockMvcBuilders.standaloneSetup(sentimentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sentiment createEntity(EntityManager em) {
        Sentiment sentiment = new Sentiment()
            .keyword(DEFAULT_KEYWORD)
            .sentiment(DEFAULT_SENTIMENT)
            .platform(DEFAULT_PLATFORM);
        return sentiment;
    }

    @Before
    public void initTest() {
        sentiment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSentiment() throws Exception {
        int databaseSizeBeforeCreate = sentimentRepository.findAll().size();

        // Create the Sentiment
        restSentimentMockMvc.perform(post("/api/sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentiment)))
            .andExpect(status().isCreated());

        // Validate the Sentiment in the database
        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeCreate + 1);
        Sentiment testSentiment = sentimentList.get(sentimentList.size() - 1);
        assertThat(testSentiment.getKeyword()).isEqualTo(DEFAULT_KEYWORD);
        assertThat(testSentiment.getSentiment()).isEqualTo(DEFAULT_SENTIMENT);
        assertThat(testSentiment.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
    }

    @Test
    @Transactional
    public void createSentimentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sentimentRepository.findAll().size();

        // Create the Sentiment with an existing ID
        sentiment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSentimentMockMvc.perform(post("/api/sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentiment)))
            .andExpect(status().isBadRequest());

        // Validate the Sentiment in the database
        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeywordIsRequired() throws Exception {
        int databaseSizeBeforeTest = sentimentRepository.findAll().size();
        // set the field null
        sentiment.setKeyword(null);

        // Create the Sentiment, which fails.

        restSentimentMockMvc.perform(post("/api/sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentiment)))
            .andExpect(status().isBadRequest());

        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSentimentIsRequired() throws Exception {
        int databaseSizeBeforeTest = sentimentRepository.findAll().size();
        // set the field null
        sentiment.setSentiment(null);

        // Create the Sentiment, which fails.

        restSentimentMockMvc.perform(post("/api/sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentiment)))
            .andExpect(status().isBadRequest());

        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlatformIsRequired() throws Exception {
        int databaseSizeBeforeTest = sentimentRepository.findAll().size();
        // set the field null
        sentiment.setPlatform(null);

        // Create the Sentiment, which fails.

        restSentimentMockMvc.perform(post("/api/sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentiment)))
            .andExpect(status().isBadRequest());

        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSentiments() throws Exception {
        // Initialize the database
        sentimentRepository.saveAndFlush(sentiment);

        // Get all the sentimentList
        restSentimentMockMvc.perform(get("/api/sentiments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sentiment.getId().intValue())))
            .andExpect(jsonPath("$.[*].keyword").value(hasItem(DEFAULT_KEYWORD.toString())))
            .andExpect(jsonPath("$.[*].sentiment").value(hasItem(DEFAULT_SENTIMENT.toString())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())));
    }
    

    @Test
    @Transactional
    public void getSentiment() throws Exception {
        // Initialize the database
        sentimentRepository.saveAndFlush(sentiment);

        // Get the sentiment
        restSentimentMockMvc.perform(get("/api/sentiments/{id}", sentiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sentiment.getId().intValue()))
            .andExpect(jsonPath("$.keyword").value(DEFAULT_KEYWORD.toString()))
            .andExpect(jsonPath("$.sentiment").value(DEFAULT_SENTIMENT.toString()))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSentiment() throws Exception {
        // Get the sentiment
        restSentimentMockMvc.perform(get("/api/sentiments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSentiment() throws Exception {
        // Initialize the database
        sentimentRepository.saveAndFlush(sentiment);

        int databaseSizeBeforeUpdate = sentimentRepository.findAll().size();

        // Update the sentiment
        Sentiment updatedSentiment = sentimentRepository.findById(sentiment.getId()).get();
        // Disconnect from session so that the updates on updatedSentiment are not directly saved in db
        em.detach(updatedSentiment);
        updatedSentiment
            .keyword(UPDATED_KEYWORD)
            .sentiment(UPDATED_SENTIMENT)
            .platform(UPDATED_PLATFORM);

        restSentimentMockMvc.perform(put("/api/sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSentiment)))
            .andExpect(status().isOk());

        // Validate the Sentiment in the database
        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeUpdate);
        Sentiment testSentiment = sentimentList.get(sentimentList.size() - 1);
        assertThat(testSentiment.getKeyword()).isEqualTo(UPDATED_KEYWORD);
        assertThat(testSentiment.getSentiment()).isEqualTo(UPDATED_SENTIMENT);
        assertThat(testSentiment.getPlatform()).isEqualTo(UPDATED_PLATFORM);
    }

    @Test
    @Transactional
    public void updateNonExistingSentiment() throws Exception {
        int databaseSizeBeforeUpdate = sentimentRepository.findAll().size();

        // Create the Sentiment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSentimentMockMvc.perform(put("/api/sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentiment)))
            .andExpect(status().isBadRequest());

        // Validate the Sentiment in the database
        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSentiment() throws Exception {
        // Initialize the database
        sentimentRepository.saveAndFlush(sentiment);

        int databaseSizeBeforeDelete = sentimentRepository.findAll().size();

        // Get the sentiment
        restSentimentMockMvc.perform(delete("/api/sentiments/{id}", sentiment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sentiment> sentimentList = sentimentRepository.findAll();
        assertThat(sentimentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sentiment.class);
        Sentiment sentiment1 = new Sentiment();
        sentiment1.setId(1L);
        Sentiment sentiment2 = new Sentiment();
        sentiment2.setId(sentiment1.getId());
        assertThat(sentiment1).isEqualTo(sentiment2);
        sentiment2.setId(2L);
        assertThat(sentiment1).isNotEqualTo(sentiment2);
        sentiment1.setId(null);
        assertThat(sentiment1).isNotEqualTo(sentiment2);
    }
}
