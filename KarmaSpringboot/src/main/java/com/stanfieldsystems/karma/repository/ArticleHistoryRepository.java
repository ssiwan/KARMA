package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.ArticleHistory;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the ArticleHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory, Long> {
	
    @Query("select article_history from ArticleHistory article_history where article_history.user.login = ?#{principal.username}")
    List<ArticleHistory> findByUserIsCurrentUser();

    @Query(value ="SELECT a.ID, a.DATE_ACCESSED , a.ARTICLE_ID, a.USER_ID " + 
    		"from ( " + 
    		"   SELECT ARTICLE_ID, MAX(DATE_ACCESSED) as maxDate " + 
    		"   from ARTICLE_HISTORY " + 
    		"   group by ARTICLE_ID " + 
    		") as x inner join ARTICLE_HISTORY as a on a.ARTICLE_ID = x.ARTICLE_ID "
    		+ "and a.DATE_ACCESSED  = x.maxDate " + 
    		"where a.DATE_ACCESSED  >:monthsAgo AND a.USER_ID = :userId " + 
    		"ORDER BY a.DATE_ACCESSED  DESC", nativeQuery = true)
    List<ArticleHistory> findRecentlyAccessedArticles(@Param("userId") Long userId, @Param("monthsAgo") ZonedDateTime monthsAgo);
    
}
