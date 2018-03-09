package com.stanfieldsystems.karma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stanfieldsystems.karma.domain.Space;
import com.stanfieldsystems.karma.domain.SpaceHistory;
import com.stanfieldsystems.karma.domain.User;
import com.stanfieldsystems.karma.repository.SpaceHistoryRepository;
import com.stanfieldsystems.karma.repository.SpaceRepository;
import com.stanfieldsystems.karma.repository.UserRepository;
import com.stanfieldsystems.karma.web.rest.errors.BadRequestAlertException;
import com.stanfieldsystems.karma.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * REST controller for managing Space.
 */
@RestController
@RequestMapping("/api")
public class SpaceResource {

    private final Logger log = LoggerFactory.getLogger(SpaceResource.class);

    private static final String ENTITY_NAME = "space";

    private final SpaceRepository spaceRepository;
    
    private final SpaceHistoryRepository spaceHistoryRepository;
    
    private final UserRepository userRepository;
    
    private Date threeMonthsago = DateUtils.addMonths(new Date(), -3); 
    private ZonedDateTime monthsAgo = threeMonthsago.toInstant().atZone(ZoneId.systemDefault()); 

    public SpaceResource(SpaceRepository spaceRepository, SpaceHistoryRepository spaceHistoryRepository,
    		UserRepository userRepository) {
        this.spaceRepository = spaceRepository;
        this.spaceHistoryRepository = spaceHistoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /spaces : Create a new space.
     *
     * @param space the space to create
     * @return the ResponseEntity with status 201 (Created) and with body the new space, or with status 400 (Bad Request) if the space has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/spaces")
    @Timed
    public ResponseEntity<Space> createSpace(@Valid @RequestBody Space space) throws URISyntaxException {
        log.debug("REST request to save Space : {}", space);
        if (space.getId() != null) {
            throw new BadRequestAlertException("A new space cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Space result = spaceRepository.save(space);
        return ResponseEntity.created(new URI("/api/spaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spaces : Updates an existing space.
     *
     * @param space the space to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated space,
     * or with status 400 (Bad Request) if the space is not valid,
     * or with status 500 (Internal Server Error) if the space couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/spaces")
    @Timed
    public ResponseEntity<Space> updateSpace(@Valid @RequestBody Space space) throws URISyntaxException {
        log.debug("REST request to update Space : {}", space);
        if (space.getId() == null) {
            return createSpace(space);
        }
        Space result = spaceRepository.save(space);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, space.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spaces : get all the spaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of spaces in body
     */
    @GetMapping("/spaces")
    @Timed
    public List<Space> getAllSpaces() {
        log.debug("REST request to get all Spaces");
        return spaceRepository.findAll();
        }

    /**
     * GET  /spaces/:id : get the "id" space.
     *
     * @param id the id of the space to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the space, or with status 404 (Not Found)
     */
    @GetMapping("/spaces/{id}")
    @Timed
    public ResponseEntity<Space> getSpace(@PathVariable Long id) {
        log.debug("REST request to get Space : {}", id);
        Space space = spaceRepository.findOne(id);
        
        if(space != null) {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	if(auth != null) { //write history for current user
        		String userName = auth.getName();
                User currentUser = userRepository.findOneByLogin(userName).orElseThrow(null);
                
                SpaceHistory spaceHistory = new SpaceHistory();
                spaceHistory.setDateAccessed(ZonedDateTime.now(ZoneId.systemDefault()));
                spaceHistory.setSpace(space);
                spaceHistory.setUser(currentUser);
                spaceHistoryRepository.save(spaceHistory);
        	}
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(space));
    }

    /**
     * DELETE  /spaces/:id : delete the "id" space.
     *
     * @param id the id of the space to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/spaces/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        log.debug("REST request to delete Space : {}", id);
        spaceHistoryRepository.deleteSpaceHistoryBySpaceId(id);
        spaceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /spaces/:countByUserId : get count of Spaces matching the "userId".
     *
     * @param id of the space of the space to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body of count, or with status 404 (Not Found)
     */
    @GetMapping("/spaces/countByUserId/{userId}")
    @Timed
    public ResponseEntity<Integer> getCountOfSpaceByUserId(@PathVariable Long userId) {
    	log.debug("REST request to get page of Articles by Space");
    	int count = spaceRepository.getSpaceCountByUserId(userId);
    	return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    /** 
     * GET /spaces/recentlyAccessed/:userId : get recently accessed spaces for a specific user. 
     * 
     * @param Long the userId of the user to retrieve recently accessed space history  
     * @return the ResponseEntity with status 200 (OK) and with body of list of spaces, or with status 404 (Not Found) 
     */ 
    @GetMapping("/spaces/recentlyAccessed/{userId}") 
    @Timed 
    public ResponseEntity<List<Space>> getRecentlyAccessedSpaces(@PathVariable Long userId) { 
    	log.debug("REST request to get page of Articles"); 
        
        List<Space> spaces = spaceRepository.findRecentlyAccessedSpaces(userId, monthsAgo);
        
        return new ResponseEntity<>(spaces, HttpStatus.OK); 
    }
    
}
