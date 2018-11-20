package com.exacs.ecra.repositories;

import com.exacs.ecra.entities.model.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RackRepository extends JpaRepository<Rack, Long> {

    @Transactional(readOnly=true)
    Rack findById(@Param("id") long id);

    // Test for native query; You can give native queries, that is, direct Oracle queries
    @Query(value = "select id from rack", nativeQuery=true)
    List<Integer> findRackIds();

}
