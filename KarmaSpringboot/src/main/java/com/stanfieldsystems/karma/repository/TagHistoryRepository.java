package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.TagHistory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TagHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagHistoryRepository extends JpaRepository<TagHistory, Long> {

    @Query("select tag_history from TagHistory tag_history where tag_history.user.login = ?#{principal.username}")
    List<TagHistory> findByUserIsCurrentUser();
    
    @Transactional
    @Modifying
    @Query("delete from TagHistory tagHistory where tagHistory.user.id =:userId")
    void deleteTagHistoryByUserId(@Param("userId") Long userId);
   

}
