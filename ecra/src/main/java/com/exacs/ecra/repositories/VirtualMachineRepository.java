package com.exacs.ecra.repositories;

import com.exacs.ecra.entities.model.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {

    @Transactional(readOnly=true)
    VirtualMachine findById(@Param("id") long id);

    @Transactional(readOnly=true)
    @Query("select vm from VirtualMachine vm where vm.computeNode.id = :nodeId and vm.rackSlot.id = :clusterId")
    VirtualMachine findVMForClusterAndNode(@Param("clusterId") long clusterId, @Param("nodeId") long nodeId);

}