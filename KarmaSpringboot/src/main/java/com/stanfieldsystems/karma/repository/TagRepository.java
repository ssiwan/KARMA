package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.Tag;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	@Query(value =" SELECT tag.* from TAG tag inner join ( "
	 		+ "  SELECT a.TAG_ID " +  
		        "from ( " +  
		        "   SELECT TAG_ID, MAX(DATE_ACCESSED) as maxDate " +  
		        "   from TAG_HISTORY " +  
		        "   group by TAG_ID " +  
		        ") as x inner join TAG_HISTORY as a on a.TAG_ID = x.TAG_ID " 
		        + "and a.DATE_ACCESSED  = x.maxDate " +  
		        "where a.DATE_ACCESSED  >:monthsAgo AND a.USER_ID = :userId " +  
		        "ORDER BY a.DATE_ACCESSED DESC LIMIT 5) sub on sub.TAG_ID = tag.id ", nativeQuery = true) 
	List<Tag> findRecentlyAccessedTags(@Param("userId") Long userId, @Param("monthsAgo") ZonedDateTime monthsAgo); 
}
