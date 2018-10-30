package com.exacs.ecra.services.inf;

import com.exacs.ecra.entities.model.Rack;
import com.exacs.ecra.entities.request.RackRequest;

import java.util.List;

public interface RackService {

    List<Rack> getRacks();

    Rack getRack(long rackIdentifier);

    Rack createRack(RackRequest rackRequest);

    void deleteRack(long rackIdentifier);

}
