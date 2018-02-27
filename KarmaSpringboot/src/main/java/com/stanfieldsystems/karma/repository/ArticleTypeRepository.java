package com.stanfieldsystems.karma.repository;

import com.stanfieldsystems.karma.domain.ArticleType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArticleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleType, Long> {

}
