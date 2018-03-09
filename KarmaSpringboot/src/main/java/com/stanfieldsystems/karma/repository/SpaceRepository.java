package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.Space;
import com.stanfieldsystems.karma.domain.Tag;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Space entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    @Query("select space from Space space where space.user.login = ?#{principal.username}")
    List<Space> findByUserIsCurrentUser();
    
    @Query("select count(*) from Space space where space.user.id =:userId") 
    int getSpaceCountByUserId(@Param("userId") Long userId); 
    
    @Query(value =" SELECT space.* from SPACE space inner join ( "
	 		+ "  SELECT a.SPACE_ID " +  
		        "from ( " +  
		        "   SELECT SPACE_ID, MAX(DATE_ACCESSED) as maxDate " +  
		        "   from SPACE_HISTORY " +  
		        "   group by SPACE_ID " +  
		        ") as x inner join SPACE_HISTORY as a on a.SPACE_ID = x.SPACE_ID " 
		        + "and a.DATE_ACCESSED  = x.maxDate " +  
		        "where a.DATE_ACCESSED  >:monthsAgo AND a.USER_ID = :userId " +  
		        "ORDER BY a.DATE_ACCESSED DESC LIMIT 5) sub on sub.SPACE_ID = space.id ", nativeQuery = true) 
	List<Space> findRecentlyAccessedSpaces(@Param("userId") Long userId, @Param("monthsAgo") ZonedDateTime monthsAgo); 

}
