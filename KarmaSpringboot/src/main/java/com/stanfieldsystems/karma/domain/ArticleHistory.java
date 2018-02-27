package com.stanfieldsystems.karma.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ArticleHistory.
 */
@Entity
@Table(name = "article_history")
public class ArticleHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_accessed", nullable = false)
    private ZonedDateTime dateAccessed;

    @ManyToOne
    private Article article;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateAccessed() {
        return dateAccessed;
    }

    public ArticleHistory dateAccessed(ZonedDateTime dateAccessed) {
        this.dateAccessed = dateAccessed;
        return this;
    }

    public void setDateAccessed(ZonedDateTime dateAccessed) {
        this.dateAccessed = dateAccessed;
    }

    public Article getArticle() {
        return article;
    }

    public ArticleHistory article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public ArticleHistory user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        ArticleHistory articleHistory = (ArticleHistory) o;
        if (articleHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articleHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticleHistory{" +
            "id=" + getId() +
            ", dateAccessed='" + getDateAccessed() + "'" +
            "}";
    }
}
