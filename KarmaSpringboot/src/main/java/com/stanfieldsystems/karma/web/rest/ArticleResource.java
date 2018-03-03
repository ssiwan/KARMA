package com.stanfieldsystems.karma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stanfieldsystems.karma.domain.Article;

import com.stanfieldsystems.karma.repository.ArticleRepository;
import com.stanfieldsystems.karma.web.rest.errors.BadRequestAlertException;
import com.stanfieldsystems.karma.web.rest.util.HeaderUtil;
import com.stanfieldsystems.karma.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Article.
 */
@Transactional
@RestController
@RequestMapping("/api")
public class ArticleResource {

    private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

    private static final String ENTITY_NAME = "article";

    private final ArticleRepository articleRepository;

    public ArticleResource(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * POST  /articles : Create a new article.
     *
     * @param article the article to create
     * @return the ResponseEntity with status 201 (Created) and with body the new article, or with status 400 (Bad Request) if the article has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/articles")
    @Timed
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) throws URISyntaxException {
        log.debug("REST request to save Article : {}", article);
        if (article.getId() != null) {
            throw new BadRequestAlertException("A new article cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Article result = articleRepository.save(article);
        return ResponseEntity.created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /articles : Updates an existing article.
     *
     * @param article the article to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated article,
     * or with status 400 (Bad Request) if the article is not valid,
     * or with status 500 (Internal Server Error) if the article couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/articles")
    @Timed
    public ResponseEntity<Article> updateArticle(@Valid @RequestBody Article article) throws URISyntaxException {
        log.debug("REST request to update Article : {}", article);
        if (article.getId() == null) {
            return createArticle(article);
        }
        Article result = articleRepository.save(article);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, article.getId().toString()))
            .body(result);
    }

    /**
     * GET  /articles : get all the articles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articles in body
     */
    @GetMapping("/articles")
    @Timed
    public ResponseEntity<List<Article>> getAllArticles(Pageable pageable) {
        log.debug("REST request to get a page of Articles");
        Page<Article> page = articleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/articles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /articles/:id : get the "id" article.
     *
     * @param id the id of the article to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the article, or with status 404 (Not Found)
     */
    @GetMapping("/articles/{id}")
    @Timed
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        log.debug("REST request to get Article : {}", id);
        Article article = articleRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(article));
    }
    
    
    /**
     * GET  /articles/searchTitles/:searchTitle : get Articles matching the "searchTitle".
     *
     * @param String that the title of the article to retrieve contains
     * @return the ResponseEntity with status 200 (OK) and with body of list of articles, or with status 404 (Not Found)
     */
    @GetMapping("/articles/searchTitles/{searchTitle}")
    @Timed
    public ResponseEntity<List<Article>> getAllArticlesWhereTitleContains(@PathVariable String searchTitle) {
    	log.debug("REST request to get page of Articles");
    	List<Article> articles = articleRepository.findAllByTitleContains(searchTitle);
    	return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    
    /**
     * GET  /articles/frequent : get top five most frequently visited articles in the last three months.
     *
     * @return the ResponseEntity with status 200 (OK) and with body of list of articles, or with status 404 (Not Found)
     */
    @GetMapping("/articles/frequent/")
    @Timed
    public ResponseEntity<List<Article>> getTopFrequentArticles() {
    	log.debug("REST request to get page of Articles");
    	List<Article> articles = articleRepository.findFrequentArticles();
    	return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * GET  /articles/:searchSpace : get Articles matching the "searchSpace".
     *
     * @param String that the space of the article to retrieve contains
     * @return the ResponseEntity with status 200 (OK) and with body of list of articles, or with status 404 (Not Found)
     */
    @GetMapping("/articles/searchSpaces/{searchSpace}")
    @Timed
    public ResponseEntity<List<Article>> getAllArticlesWhereSpaceContains(@PathVariable String searchSpace) {
    	log.debug("REST request to get page of Articles by Space");
    	List<Article> articles = articleRepository.findAllBySpaceContains(searchSpace);
    	return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    
    
    /** 
     * GET /articles/recentlyAccessed/:userId : get recently accessed articles for a specific user. 
     * 
     * @param Long the userId of the user to retrieve recently accessed article history  
     * @return the ResponseEntity with status 200 (OK) and with body of list of articles, or with status 404 (Not Found) 
     */ 
    @GetMapping("/articles/recentlyAccessed/{userId}") 
    @Timed 
    public ResponseEntity<List<Article>> getRecentlyAccessedArticles(@PathVariable Long userId) { 
    	log.debug("REST request to get page of Articles"); 
        Date threeMonthsago = DateUtils.addMonths(new Date(), -3); 
        ZonedDateTime monthsAgo = threeMonthsago.toInstant().atZone(ZoneId.systemDefault()); 
        
        List<Article> articles = articleRepository.findRecentlyAccessedArticles(userId, monthsAgo);
        
        return new ResponseEntity<>(articles, HttpStatus.OK); 
    }
    /**
     * DELETE  /articles/:id : delete the "id" article.
     *
     * @param id the id of the article to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/articles/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        log.debug("REST request to delete Article : {}", id);
        articleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}