package com.nomesdeusuariosdisponiveis.repositories;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.nomesdeusuariosdisponiveis.entities.Site;

@Lazy
@Repository
@Transactional(readOnly = true)
public interface SiteRepository extends JpaRepository<Site, Long> {

	Optional<Site> findByService(String service);

}
