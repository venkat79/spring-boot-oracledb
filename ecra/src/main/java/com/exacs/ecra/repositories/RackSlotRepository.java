package com.exacs.ecra.repositories;


import com.exacs.ecra.entities.model.RackSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RackSlotRepository extends JpaRepository<RackSlot, Long> {

    @Transactional(readOnly=true)
    @Query("Select rackslot from RackSlot rackslot join rackslot.rack where rackslot.rack.id = :id")
    List<RackSlot> getAllClusterForRack(@Param("id") long id);

    @Transactional(readOnly=true)
    @Query("Select rackslot from RackSlot rackslot join rackslot.rack where rackslot.rack.id = :rackId and rackslot.id = :id")
    RackSlot findClusterForRack(@Param("rackId") long rackId, @Param("id") long id);

    @Transactional(readOnly=true)
    RackSlot findById(@Param("id") long id);

}
