package com.stanfieldsystems.karma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stanfieldsystems.karma.domain.SpaceHistory;

import com.stanfieldsystems.karma.repository.SpaceHistoryRepository;
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
 * REST controller for managing SpaceHistory.
 */
@RestController
@RequestMapping("/api")
public class SpaceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SpaceHistoryResource.class);

    private static final String ENTITY_NAME = "spaceHistory";

    private final SpaceHistoryRepository spaceHistoryRepository;

    public SpaceHistoryResource(SpaceHistoryRepository spaceHistoryRepository) {
        this.spaceHistoryRepository = spaceHistoryRepository;
    }

    /**
     * POST  /space-histories : Create a new spaceHistory.
     *
     * @param spaceHistory the spaceHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new spaceHistory, or with status 400 (Bad Request) if the spaceHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/space-histories")
    @Timed
    public ResponseEntity<SpaceHistory> createSpaceHistory(@Valid @RequestBody SpaceHistory spaceHistory) throws URISyntaxException {
        log.debug("REST request to save SpaceHistory : {}", spaceHistory);
        if (spaceHistory.getId() != null) {
            throw new BadRequestAlertException("A new spaceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpaceHistory result = spaceHistoryRepository.save(spaceHistory);
        return ResponseEntity.created(new URI("/api/space-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /space-histories : Updates an existing spaceHistory.
     *
     * @param spaceHistory the spaceHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated spaceHistory,
     * or with status 400 (Bad Request) if the spaceHistory is not valid,
     * or with status 500 (Internal Server Error) if the spaceHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/space-histories")
    @Timed
    public ResponseEntity<SpaceHistory> updateSpaceHistory(@Valid @RequestBody SpaceHistory spaceHistory) throws URISyntaxException {
        log.debug("REST request to update SpaceHistory : {}", spaceHistory);
        if (spaceHistory.getId() == null) {
            return createSpaceHistory(spaceHistory);
        }
        SpaceHistory result = spaceHistoryRepository.save(spaceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, spaceHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /space-histories : get all the spaceHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of spaceHistories in body
     */
    @GetMapping("/space-histories")
    @Timed
    public List<SpaceHistory> getAllSpaceHistories() {
        log.debug("REST request to get all SpaceHistories");
        return spaceHistoryRepository.findAll();
        }

    /**
     * GET  /space-histories/:id : get the "id" spaceHistory.
     *
     * @param id the id of the spaceHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the spaceHistory, or with status 404 (Not Found)
     */
    @GetMapping("/space-histories/{id}")
    @Timed
    public ResponseEntity<SpaceHistory> getSpaceHistory(@PathVariable Long id) {
        log.debug("REST request to get SpaceHistory : {}", id);
        SpaceHistory spaceHistory = spaceHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(spaceHistory));
    }

    /**
     * DELETE  /space-histories/:id : delete the "id" spaceHistory.
     *
     * @param id the id of the spaceHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/space-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpaceHistory(@PathVariable Long id) {
        log.debug("REST request to delete SpaceHistory : {}", id);
        spaceHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
