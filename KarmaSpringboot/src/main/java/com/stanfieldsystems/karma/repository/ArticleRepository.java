package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.Article;
import com.stanfieldsystems.karma.domain.ArticleHistory;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
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
    
    @Query(value ="SELECT * from ARTICLE WHERE ID in ( " 
            + " SELECT a.ARTICLE_ID " +   
                "   from ( " +   
                "       SELECT ARTICLE_ID, MAX(DATE_ACCESSED) as maxDate " +   
                "       from ARTICLE_HISTORY " +   
                "       group by ARTICLE_ID " +   
                "   ) as x inner join ARTICLE_HISTORY as a on a.ARTICLE_ID = x.ARTICLE_ID "  
                + "and a.DATE_ACCESSED  = x.maxDate " +   
                "where a.DATE_ACCESSED  >:monthsAgo AND a.USER_ID = :userId " +   
                "ORDER BY a.DATE_ACCESSED DESC LIMIT 10)", nativeQuery = true)  
        List<Article> findRecentlyAccessedArticles(@Param("userId") Long userId, @Param("monthsAgo") ZonedDateTime monthsAgo);  
            

    @Query(value = "Select article.Id, Count(*) From  (" + 
    		"Select a.iD FROM Article a Inner Join ArticleHistor ah ON a.iD = ah.articleID where ah.dateAccessed >  DATEADD(month, -3, GETDATE())) b" + 
    		"Group By ID" + 
    		"ORDER BY Count(*) DESC LIMIT 5", nativeQuery = true)
    List<Article> findFrequentArticles();
}