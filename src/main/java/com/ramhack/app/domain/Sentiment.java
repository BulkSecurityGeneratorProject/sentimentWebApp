package com.ramhack.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Sentiment.
 */
@Entity
@Table(name = "sentiment")
public class Sentiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "keyword", nullable = false)
    private String keyword;

    @NotNull
    @Column(name = "sentiment", nullable = false)
    private String sentiment;

    @NotNull
    @Column(name = "platform", nullable = false)
    private String platform;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public Sentiment keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSentiment() {
        return sentiment;
    }

    public Sentiment sentiment(String sentiment) {
        this.sentiment = sentiment;
        return this;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getPlatform() {
        return platform;
    }

    public Sentiment platform(String platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sentiment sentiment = (Sentiment) o;
        if (sentiment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sentiment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sentiment{" +
            "id=" + getId() +
            ", keyword='" + getKeyword() + "'" +
            ", sentiment='" + getSentiment() + "'" +
            ", platform='" + getPlatform() + "'" +
            "}";
    }
}
