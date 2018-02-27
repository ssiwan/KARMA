package com.stanfieldsystems.karma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stanfieldsystems.karma.domain.TagHistory;

import com.stanfieldsystems.karma.repository.TagHistoryRepository;
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
 * REST controller for managing TagHistory.
 */
@RestController
@RequestMapping("/api")
public class TagHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TagHistoryResource.class);

    private static final String ENTITY_NAME = "tagHistory";

    private final TagHistoryRepository tagHistoryRepository;

    public TagHistoryResource(TagHistoryRepository tagHistoryRepository) {
        this.tagHistoryRepository = tagHistoryRepository;
    }

    /**
     * POST  /tag-histories : Create a new tagHistory.
     *
     * @param tagHistory the tagHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tagHistory, or with status 400 (Bad Request) if the tagHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tag-histories")
    @Timed
    public ResponseEntity<TagHistory> createTagHistory(@Valid @RequestBody TagHistory tagHistory) throws URISyntaxException {
        log.debug("REST request to save TagHistory : {}", tagHistory);
        if (tagHistory.getId() != null) {
            throw new BadRequestAlertException("A new tagHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagHistory result = tagHistoryRepository.save(tagHistory);
        return ResponseEntity.created(new URI("/api/tag-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tag-histories : Updates an existing tagHistory.
     *
     * @param tagHistory the tagHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tagHistory,
     * or with status 400 (Bad Request) if the tagHistory is not valid,
     * or with status 500 (Internal Server Error) if the tagHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tag-histories")
    @Timed
    public ResponseEntity<TagHistory> updateTagHistory(@Valid @RequestBody TagHistory tagHistory) throws URISyntaxException {
        log.debug("REST request to update TagHistory : {}", tagHistory);
        if (tagHistory.getId() == null) {
            return createTagHistory(tagHistory);
        }
        TagHistory result = tagHistoryRepository.save(tagHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tagHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tag-histories : get all the tagHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tagHistories in body
     */
    @GetMapping("/tag-histories")
    @Timed
    public List<TagHistory> getAllTagHistories() {
        log.debug("REST request to get all TagHistories");
        return tagHistoryRepository.findAll();
        }

    /**
     * GET  /tag-histories/:id : get the "id" tagHistory.
     *
     * @param id the id of the tagHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tagHistory, or with status 404 (Not Found)
     */
    @GetMapping("/tag-histories/{id}")
    @Timed
    public ResponseEntity<TagHistory> getTagHistory(@PathVariable Long id) {
        log.debug("REST request to get TagHistory : {}", id);
        TagHistory tagHistory = tagHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tagHistory));
    }

    /**
     * DELETE  /tag-histories/:id : delete the "id" tagHistory.
     *
     * @param id the id of the tagHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tag-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteTagHistory(@PathVariable Long id) {
        log.debug("REST request to delete TagHistory : {}", id);
        tagHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
