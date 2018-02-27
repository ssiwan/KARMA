package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.ArticleHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ArticleHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory, Long> {

    @Query("select article_history from ArticleHistory article_history where article_history.user.login = ?#{principal.username}")
    List<ArticleHistory> findByUserIsCurrentUser();

}
