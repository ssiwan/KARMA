package com.stanfieldsystems.karma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stanfieldsystems.karma.domain.Tag;

import com.stanfieldsystems.karma.repository.TagRepository;
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
 * REST controller for managing Tag.
 */
@RestController
@RequestMapping("/api")
public class TagResource {

	private final Logger log = LoggerFactory.getLogger(TagResource.class);

	private static final String ENTITY_NAME = "tag";

	private final TagRepository tagRepository;

	public TagResource(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	/**
	 * POST /tags : Create a new tag.
	 *
	 * @param tag
	 *            the tag to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         tag, or with status 400 (Bad Request) if the tag has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/tags")
	@Timed
	public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
		log.debug("REST request to save Tag : {}", tag);
		if (tag.getId() != null) {
			throw new BadRequestAlertException("A new tag cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Tag result = tagRepository.save(tag);
		return ResponseEntity.created(new URI("/api/tags/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /tags : Updates an existing tag.
	 *
	 * @param tag
	 *            the tag to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         tag, or with status 400 (Bad Request) if the tag is not valid, or
	 *         with status 500 (Internal Server Error) if the tag couldn't be
	 *         updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/tags")
	@Timed
	public ResponseEntity<Tag> updateTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
		log.debug("REST request to update Tag : {}", tag);
		if (tag.getId() == null) {
			return createTag(tag);
		}
		Tag result = tagRepository.save(tag);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tag.getId().toString()))
				.body(result);
	}

	/**
	 * GET /tags : get all the tags.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of tags in body
	 */
	@GetMapping("/tags")
	@Timed
	public ResponseEntity<List<Tag>> getAllTags(Pageable pageable) {
		log.debug("REST request to get a page of Tags");
		Page<Tag> page = tagRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tags");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /tags/:id : get the "id" tag.
	 *
	 * @param id
	 *            the id of the tag to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the tag, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/tags/{id}")
	@Timed
	public ResponseEntity<Tag> getTag(@PathVariable Long id) {
		log.debug("REST request to get Tag : {}", id);
		Tag tag = tagRepository.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tag));
	}

	/**
	 * GET /tags/recentlyAccessed/:userId : get recently accessed articles for a
	 * specific user.
	 * 
	 * @param Long
	 *            the userId of the user to retrieve recently accessed tag history
	 * @return the ResponseEntity with status 200 (OK) and with body of list of
	 *         articles, or with status 404 (Not Found)
	 */
	@GetMapping("/tags/recentlyAccessed/{userId}")
	@Timed
	public ResponseEntity<List<Tag>> getRecentlyAccessedArticles(@PathVariable Long userId) {
		log.debug("REST request to get page of Articles");
		Date threeMonthsago = DateUtils.addMonths(new Date(), -3);
		ZonedDateTime monthsAgo = threeMonthsago.toInstant().atZone(ZoneId.systemDefault());

		List<Tag> tags = tagRepository.findRecentlyAccessedTags(userId, monthsAgo);

		return new ResponseEntity<>(tags, HttpStatus.OK);
	}

	/**
	 * DELETE /tags/:id : delete the "id" tag.
	 *
	 * @param id
	 *            the id of the tag to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/tags/{id}")
	@Timed
	public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
		log.debug("REST request to delete Tag : {}", id);
		tagRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
