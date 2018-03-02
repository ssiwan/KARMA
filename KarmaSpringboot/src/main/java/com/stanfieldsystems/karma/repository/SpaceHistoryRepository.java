package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.SpaceHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the SpaceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpaceHistoryRepository extends JpaRepository<SpaceHistory, Long> {

    @Query("select space_history from SpaceHistory space_history where space_history.user.login = ?#{principal.username}")
    List<SpaceHistory> findByUserIsCurrentUser();

}
