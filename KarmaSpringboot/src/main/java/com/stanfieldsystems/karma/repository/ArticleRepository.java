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
    
    @Query("select article from Article article where Lower(article.title) like Lower(CONCAT('%',:searchString,'%'))")
    List<Article> findAllByTitleContains(@Param("searchString") String searchString);
    
    @Query(value="select article.* from Article article where article.space_Id =:spaceId", nativeQuery=true)
    List<Article> findAllBySpaceId(@Param("spaceId") int spaceId);
    
    @Query(value="select count(*) from Article article  where article.space_Id =:spaceId", nativeQuery=true)
    int getArticleCountBySpaceId(@Param("spaceId") int spaceId);
    
    @Query("select count(*) from Article article  where article.user.id =:userId")
    int getArticleCountByUserId(@Param("userId") Long userId);
    
    @Query(value ="SELECT article.* from ARTICLE article inner join ( " +
                "   SELECT a.ARTICLE_ID " +   
                "   from ( " +   
                "       SELECT ARTICLE_ID, MAX(DATE_ACCESSED) as maxDate " +   
                "       from ARTICLE_HISTORY " +   
                "       group by ARTICLE_ID " +   
                "   ) as x inner join ARTICLE_HISTORY as a on a.ARTICLE_ID = x.ARTICLE_ID "  
                + "and a.DATE_ACCESSED  = x.maxDate " +   
                "where a.DATE_ACCESSED  >:monthsAgo AND a.USER_ID = :userId " +   
                "ORDER BY a.DATE_ACCESSED DESC LIMIT 5) sub on sub.ARTICLE_ID = article.id ", nativeQuery = true)  
        List<Article> findRecentlyAccessedArticles(@Param("userId") Long userId, @Param("monthsAgo") ZonedDateTime monthsAgo);  
            

    @Query(value = "Select article.* from Article article where article.Id In ( " + 
    		"Select b.Id From  ( " + 
    		"    		Select a.iD FROM Article a Inner Join Article_History ah ON a.ID = ah.ARTICLE_ID " +
    		"           where ah.DATE_ACCESSED  > :monthsAgo ) b " + 
    		"    		Group By ID  " + 
    		"    		ORDER BY Count(*) DESC LIMIT 5 )", nativeQuery = true)
    List<Article> findFrequentArticles(@Param("monthsAgo") ZonedDateTime monthsAgo);
    
    @Query(value= "SELECT * FROM ARTICLE WHERE ID IN "
    		+ "(SELECT ARTICLES_ID FROM ARTICLE_TAG where TAGS_ID = :tagId)", nativeQuery = true)
    List<Article> findArticleByTagId(@Param("tagId") Long tagId);
}