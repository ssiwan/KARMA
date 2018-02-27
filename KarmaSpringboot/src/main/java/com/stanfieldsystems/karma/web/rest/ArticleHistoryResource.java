package com.stanfieldsystems.karma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stanfieldsystems.karma.domain.ArticleHistory;

import com.stanfieldsystems.karma.repository.ArticleHistoryRepository;
import com.stanfieldsystems.karma.web.rest.errors.BadRequestAlertException;
import com.stanfieldsystems.karma.web.rest.util.HeaderUtil;
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
 * REST controller for managing ArticleHistory.
 */
@RestController
@RequestMapping("/api")
public class ArticleHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ArticleHistoryResource.class);

    private static final String ENTITY_NAME = "articleHistory";

    private final ArticleHistoryRepository articleHistoryRepository;

    public ArticleHistoryResource(ArticleHistoryRepository articleHistoryRepository) {
        this.articleHistoryRepository = articleHistoryRepository;
    }

    /**
     * POST  /article-histories : Create a new articleHistory.
     *
     * @param articleHistory the articleHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articleHistory, or with status 400 (Bad Request) if the articleHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/article-histories")
    @Timed
    public ResponseEntity<ArticleHistory> createArticleHistory(@Valid @RequestBody ArticleHistory articleHistory) throws URISyntaxException {
        log.debug("REST request to save ArticleHistory : {}", articleHistory);
        if (articleHistory.getId() != null) {
            throw new BadRequestAlertException("A new articleHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleHistory result = articleHistoryRepository.save(articleHistory);
        return ResponseEntity.created(new URI("/api/article-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /article-histories : Updates an existing articleHistory.
     *
     * @param articleHistory the articleHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articleHistory,
     * or with status 400 (Bad Request) if the articleHistory is not valid,
     * or with status 500 (Internal Server Error) if the articleHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/article-histories")
    @Timed
    public ResponseEntity<ArticleHistory> updateArticleHistory(@Valid @RequestBody ArticleHistory articleHistory) throws URISyntaxException {
        log.debug("REST request to update ArticleHistory : {}", articleHistory);
        if (articleHistory.getId() == null) {
            return createArticleHistory(articleHistory);
        }
        ArticleHistory result = articleHistoryRepository.save(articleHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articleHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /article-histories : get all the articleHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of articleHistories in body
     */
    @GetMapping("/article-histories")
    @Timed
    public List<ArticleHistory> getAllArticleHistories() {
        log.debug("REST request to get all ArticleHistories");
        return articleHistoryRepository.findAll();
        }

    /**
     * GET  /article-histories/:id : get the "id" articleHistory.
     *
     * @param id the id of the articleHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articleHistory, or with status 404 (Not Found)
     */
    @GetMapping("/article-histories/{id}")
    @Timed
    public ResponseEntity<ArticleHistory> getArticleHistory(@PathVariable Long id) {
        log.debug("REST request to get ArticleHistory : {}", id);
        ArticleHistory articleHistory = articleHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articleHistory));
    }

    /**
     * DELETE  /article-histories/:id : delete the "id" articleHistory.
     *
     * @param id the id of the articleHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/article-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticleHistory(@PathVariable Long id) {
        log.debug("REST request to delete ArticleHistory : {}", id);
        articleHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
