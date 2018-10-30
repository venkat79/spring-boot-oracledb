package com.exacs.ecra.repositories;

import com.exacs.ecra.entities.model.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface RackRepository extends JpaRepository<Rack, Long> {

    @Transactional(readOnly=true)
    Rack findById(@Param("id") long id);

}
