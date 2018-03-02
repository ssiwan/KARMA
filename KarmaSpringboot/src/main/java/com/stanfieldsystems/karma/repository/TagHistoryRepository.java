package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.TagHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the TagHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagHistoryRepository extends JpaRepository<TagHistory, Long> {

    @Query("select tag_history from TagHistory tag_history where tag_history.user.login = ?#{principal.username}")
    List<TagHistory> findByUserIsCurrentUser();
    
    @Query(value ="SELECT a.ID, a.DATE_ACCESSED , a.TAG_ID, a.USER_ID " + 
    		"from ( " + 
    		"   SELECT TAG_ID, MAX(DATE_ACCESSED) as maxDate " + 
    		"   from TAG_HISTORY " + 
    		"   group by TAG_ID " + 
    		") as x inner join TAG_HISTORY as a on a.TAG_ID = x.TAG_ID "
    		+ "and a.DATE_ACCESSED  = x.maxDate " + 
    		"where a.DATE_ACCESSED  >:monthsAgo AND a.USER_ID = :userId " + 
    		"ORDER BY a.DATE_ACCESSED  DESC", nativeQuery = true)
    List<TagHistory> findRecentlyAccessedTags(@Param("userId") Long userId, @Param("monthsAgo") ZonedDateTime monthsAgo);

}
