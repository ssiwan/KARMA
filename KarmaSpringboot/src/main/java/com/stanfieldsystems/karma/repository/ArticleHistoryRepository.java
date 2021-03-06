package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.ArticleHistory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the ArticleHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory, Long> {

    @Query("select article_history from ArticleHistory article_history where article_history.user.login = ?#{principal.username}")
    List<ArticleHistory> findByUserIsCurrentUser();
    
    @Transactional
    @Modifying
    @Query("delete from ArticleHistory articleHistory where articleHistory.article.id =:articleId")
    void deleteArticleHistoryByArticleId(@Param("articleId") Long articleId);
   
}
