package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.SpaceHistory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SpaceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpaceHistoryRepository extends JpaRepository<SpaceHistory, Long> {

    @Query("select space_history from SpaceHistory space_history where space_history.user.login = ?#{principal.username}")
    List<SpaceHistory> findByUserIsCurrentUser();
    
    @Transactional
    @Modifying
    @Query("delete from SpaceHistory spaceHistory where spaceHistory.space.id =:spaceId")
    void deleteSpaceHistoryBySpaceId(@Param("spaceId") Long spaceId);
   

}
