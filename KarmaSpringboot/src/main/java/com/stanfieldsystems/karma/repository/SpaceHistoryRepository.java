package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.SpaceHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SpaceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpaceHistoryRepository extends JpaRepository<SpaceHistory, Long> {

}
