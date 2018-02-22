package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.Article;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select article from Article article where article.user.login = ?#{principal.username}")
    List<Article> findByUserIsCurrentUser();
    @Query("select distinct article from Article article left join fetch article.tags")
    List<Article> findAllWithEagerRelationships();

    @Query("select article from Article article left join fetch article.tags where article.id =:id")
    Article findOneWithEagerRelationships(@Param("id") Long id);

}
