package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.TagHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TagHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagHistoryRepository extends JpaRepository<TagHistory, Long> {

}
