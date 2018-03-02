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
    @Query("select distinct article from Article article left join fetch article.tags left join fetch article.articleTypes")
    List<Article> findAllWithEagerRelationships();

    @Query("select article from Article article left join fetch article.tags left join fetch article.articleTypes where article.id =:id")
    Article findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select article from Article article where article.title like CONCAT('%',:searchString,'%')")
    List<Article> findAllByTitleContains(@Param("searchString") String searchString);
    
    @Query("select article from Article article where article.space =:searchString")
    List<Article> findAllBySpaceContains(@Param("searchString") String searchString);
    
//    @Query("Select article.Id, Count(*) From Article article where (" + 
//    		"Select a.iD FROM Article a Inner Join ArticleHistor ah ON a.iD = ah.articleID where ah.dateAccessed >  DATEADD(month, -3, GETDATE())) b" + 
//    		"Group By ID" + 
//    		"ORDER BY Count(*) DESC LIMIT 5")
//    List<Article> findFrequentArticles();

}