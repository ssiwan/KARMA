package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.Space;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

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

}
