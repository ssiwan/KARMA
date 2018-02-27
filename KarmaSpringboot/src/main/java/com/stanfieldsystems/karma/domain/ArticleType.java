package com.stanfieldsystems.karma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ArticleType.
 */
@Entity
@Table(name = "article_type")
public class ArticleType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "articleTypes")
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ArticleType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public ArticleType articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public ArticleType addArticle(Article article) {
        this.articles.add(article);
        article.getArticleTypes().add(this);
        return this;
    }

    public ArticleType removeArticle(Article article) {
        this.articles.remove(article);
        article.getArticleTypes().remove(this);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
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
        ArticleType articleType = (ArticleType) o;
        if (articleType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articleType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticleType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
