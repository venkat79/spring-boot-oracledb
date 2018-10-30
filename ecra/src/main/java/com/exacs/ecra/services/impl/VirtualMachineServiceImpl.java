package com.exacs.ecra.services.impl;

import com.exacs.ecra.entities.model.ComputeNode;
import com.exacs.ecra.entities.model.Rack;
import com.exacs.ecra.entities.model.RackSlot;
import com.exacs.ecra.entities.model.VirtualMachine;
import com.exacs.ecra.entities.request.VirtualMachineRequest;
import com.exacs.ecra.repositories.RackSlotRepository;
import com.exacs.ecra.repositories.VirtualMachineRepository;
import com.exacs.ecra.services.inf.RackService;
import com.exacs.ecra.services.inf.RackSlotService;
import com.exacs.ecra.services.inf.VirtualMachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService {

    private static final Logger _logger = LoggerFactory.getLogger(VirtualMachineServiceImpl.class);

    @Autowired
    private RackSlotService rackSlotService;

    @Autowired
    private RackService rackService;

    @Autowired
    private RackSlotRepository rackSlotRespository;

    @Autowired
    private VirtualMachineRepository virtualMachineRepository;


    @Override
    @Transactional
    public RackSlot addVM(long clusterId, long nodeId, VirtualMachineRequest virtualMachineRequest) {
        RackSlot rackSlot = rackSlotService.getCluster(clusterId);

        if (rackSlot != null) {
            Rack rack = rackSlot.getRack();


            if (rack != null) {
                ComputeNode computeNode = null;
                for (ComputeNode cn : rack.getComputeNodeList()) {
                    if (cn.getId() == nodeId) {
                        computeNode = cn;
                        break;
                    }
                }


                _logger.debug("Compute Node from rack matched : {}", computeNode.toString());

                for (VirtualMachine vm : rackSlot.getVirtualMachinesList()) {
                    _logger.debug("Compute Node of VM {} : {}", vm.getId(), vm.getComputeNode().toString());
                    if (vm.getComputeNode().equals(computeNode)) {
                        _logger.debug("Matched...");
                        // Already node is part of cluster. don't add VM
                        return null;
                    } else {
                        _logger.debug("Not Matched");
                    }
                }

                VirtualMachine virtualMachine = new VirtualMachine();
                virtualMachine.setName(virtualMachineRequest.getName());
                virtualMachine.setRackSlot(rackSlot);
                virtualMachine.setComputeNode(computeNode);

                rackSlot.getVirtualMachinesList().add(virtualMachine);
                rackSlot = rackSlotRespository.saveAndFlush(rackSlot);
            }


        }

        return rackSlot;

    }

    @Override
    @Transactional
    public RackSlot removeVM(long clusterId, long nodeId, long vmId) {
        VirtualMachine virtualMachine = virtualMachineRepository.findById(vmId);
        RackSlot rackSlot = rackSlotService.getCluster(clusterId);
        if (virtualMachine != null) {

            rackSlot.getVirtualMachinesList().remove(virtualMachine);
            rackSlot = rackSlotRespository.saveAndFlush(rackSlot);

        }

        return rackSlot;

    }

    @Override
    public List<VirtualMachine> getVMs(long clusterId) {
        RackSlot rackSlot = rackSlotService.getCluster(clusterId);
        if (rackSlot != null) {
            return rackSlot.getVirtualMachinesList();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public VirtualMachine getVM(long clusterId, long nodeId) {
       return (virtualMachineRepository.findVMForClusterAndNode(clusterId, nodeId));
    }

    @Override
    public VirtualMachine getVM(long vmId) {
       return (virtualMachineRepository.getOne(vmId));
    }
}
