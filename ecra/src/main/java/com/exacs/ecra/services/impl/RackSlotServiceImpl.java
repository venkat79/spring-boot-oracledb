package com.exacs.ecra.services.impl;

import com.exacs.ecra.converters.EcraConverter;
import com.exacs.ecra.entities.model.Rack;
import com.exacs.ecra.entities.model.RackSlot;
import com.exacs.ecra.entities.request.RackSlotRequest;
import com.exacs.ecra.repositories.RackSlotRepository;
import com.exacs.ecra.services.inf.RackService;
import com.exacs.ecra.services.inf.RackSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RackSlotServiceImpl implements RackSlotService {

    @Autowired
    private RackSlotRepository rackSlotRepository;

    @Autowired
    private RackService rackService;

    @Autowired
    private EcraConverter ecraConverter;

    @Override
    @Transactional
    public RackSlot createCluster(long rackId, RackSlotRequest rackSlotRequest) {
        Rack rack = rackService.getRack(rackId);
        if (rack != null) {
            RackSlot rackSlot = ecraConverter.fromRackSlotRequest(rackSlotRequest);
            rackSlot.setRack(rack);
            rackSlot = rackSlotRepository.saveAndFlush(rackSlot);
            return rackSlot;
        } else {
            return null;
        }
    }

    @Override
    public List<RackSlot> getClusters(long rackId) {
        return rackSlotRepository.getAllClusterForRack(rackId);
    }

    @Override
    public RackSlot getCluster(long clusterId) {
        RackSlot rackSlot = rackSlotRepository.findById(clusterId);
        return rackSlot;
    }

    @Override
    public RackSlot getCluster(long rackId, long clusterId) {
       return (rackSlotRepository.findClusterForRack(rackId, clusterId));
    }

    @Transactional
    @Override
    public void deleteCluster(long clusterId) {
        rackSlotRepository.deleteById(clusterId);
    }

    @Override
    @Transactional
    public void deleteClusters() {
        rackSlotRepository.deleteAll();
    }
}
