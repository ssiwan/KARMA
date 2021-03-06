package com.stanfieldsystems.karma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stanfieldsystems.karma.domain.Article;
import com.stanfieldsystems.karma.domain.ArticleHistory;
import com.stanfieldsystems.karma.domain.SpaceHistory;
import com.stanfieldsystems.karma.domain.Tag;
import com.stanfieldsystems.karma.domain.TagHistory;
import com.stanfieldsystems.karma.domain.User;
import com.stanfieldsystems.karma.repository.ArticleHistoryRepository;
import com.stanfieldsystems.karma.repository.ArticleRepository;
import com.stanfieldsystems.karma.repository.SpaceHistoryRepository;
import com.stanfieldsystems.karma.repository.TagHistoryRepository;
import com.stanfieldsystems.karma.repository.UserRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    
    private final ArticleHistoryRepository articleHistoryRepository;
    
    private final UserRepository userRepository;
    
    private final SpaceHistoryRepository spaceHistoryRepository;
    
    private final TagHistoryRepository tagHistoryRepository;
    
    private Date threeMonthsago = DateUtils.addMonths(new Date(), -3); 
    private ZonedDateTime monthsAgo = threeMonthsago.toInstant().atZone(ZoneId.systemDefault()); 

    public ArticleResource(ArticleRepository articleRepository, ArticleHistoryRepository articleHistoryRepository, 
    		TagHistoryRepository tagHistoryRepository, UserRepository userRepository, SpaceHistoryRepository spaceHistoryRepository) {
        this.articleRepository = articleRepository;
        this.articleHistoryRepository = articleHistoryRepository;
        this.tagHistoryRepository = tagHistoryRepository;
        this.userRepository = userRepository;
        this.spaceHistoryRepository = spaceHistoryRepository;
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
        writeHistory(result);
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
        writeHistory(result);
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
     * GET  /articles : get all the articles with lazy loading.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articles in body
     */
    @GetMapping("/articles/all")
    @Timed
    public ResponseEntity<List<Article>> getAllArticlesLazy() {
        log.debug("REST request to get a page of Articles");
        List<Article> articles = articleRepository.findAllWithEagerRelationships();
       
        return new ResponseEntity<>(articles, HttpStatus.OK);
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
        
        if(article != null) {
        	writeHistory(article);
        }
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(article));
    }
    
    private void writeHistory(Article article) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	if(auth != null) { //write history for current user
    		String userName = auth.getName();
            User currentUser = userRepository.findOneByLogin(userName).orElseThrow(null);
             
         	ArticleHistory articleHistory = new ArticleHistory();
         	articleHistory.setDateAccessed(ZonedDateTime.now(ZoneId.systemDefault()));
         	articleHistory.setArticle(article);
         	articleHistory.setUser(currentUser);
         	articleHistoryRepository.save(articleHistory);
         	
         	List<Tag> tags = new ArrayList<Tag>(article.getTags());
         	for(Tag tag : tags) {
         		TagHistory tagHistory = new TagHistory();
             	tagHistory.setDateAccessed(ZonedDateTime.now(ZoneId.systemDefault()));
             	tagHistory.setTag(tag);
             	tagHistory.setUser(currentUser);
             	tagHistoryRepository.save(tagHistory);
         	}
    	}
    }
    
    /**
     * GET  /articles/:tagId : get the "tagId" article.
     *
     * @param tagId the tagId associated with the article to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the article, or with status 404 (Not Found)
     */
    @GetMapping("/articles/tag/{id}")
    @Timed
    public ResponseEntity<List<Article>> getArticleByTagId(@PathVariable Long id) {
        log.debug("REST request to get Article : {}", id);
        
        List<Article> articles = articleRepository.findArticleByTagId(id);
        
        return new ResponseEntity<>(articles, HttpStatus.OK);
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
    	List<Article> articles = articleRepository.findFrequentArticles(monthsAgo);
    	return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * GET  /articles/:spaceId : get Articles matching the "spaceId".
     *
     * @param int that the space of the article to retrieve contains
     * @return the ResponseEntity with status 200 (OK) and with body of list of articles, or with status 404 (Not Found)
     */
    @GetMapping("/articles/searchSpace/{spaceId}")
    @Timed
    public ResponseEntity<List<Article>> getAllArticlesBySpaceId(@PathVariable int spaceId) {
    	log.debug("REST request to get page of Articles by Space");
    	List<Article> articles = articleRepository.findAllBySpaceId(spaceId);
    	
    	if(articles != null && articles.get(0).getSpace() != null) {
    		
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	if(auth != null) { //write history for current user
        		String userName = auth.getName();
                User currentUser = userRepository.findOneByLogin(userName).orElseThrow(null);
                
                SpaceHistory spaceHistory = new SpaceHistory();
                spaceHistory.setDateAccessed(ZonedDateTime.now(ZoneId.systemDefault()));
                spaceHistory.setSpace(articles.get(0).getSpace());
                spaceHistory.setUser(currentUser);
                spaceHistoryRepository.save(spaceHistory);
        	}
            
    	}
    	return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    
    /**
     * GET  /articles/:countBySpace : get count of Articles matching the "spaceId".
     *
     * @param id of the space of the article to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body of count, or with status 404 (Not Found)
     */
    @GetMapping("/articles/countBySpace/{spaceId}")
    @Timed
    public ResponseEntity<Integer> getCountOfArticlesBySpaceId(@PathVariable int spaceId) {
    	log.debug("REST request to get page of Articles by Space");
    	int count = articleRepository.getArticleCountBySpaceId(spaceId);
    	return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    /**
     * GET  /articles/:countByUserId : get count of Articles matching the "spaceId".
     *
     * @param id of the space of the article to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body of count, or with status 404 (Not Found)
     */
    @GetMapping("/articles/countByUserId/{userId}")
    @Timed
    public ResponseEntity<Integer> getCountOfArticlesByUserId(@PathVariable Long userId) {
    	log.debug("REST request to get page of Articles by UserId");
    	int count = articleRepository.getArticleCountByUserId(userId);
    	return new ResponseEntity<>(count, HttpStatus.OK);
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
        articleHistoryRepository.deleteArticleHistoryByArticleId(id);
        articleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}