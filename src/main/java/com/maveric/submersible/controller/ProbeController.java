package com.maveric.submersible.controller;

import com.maveric.submersible.dto.CommandRequest;
import com.maveric.submersible.dto.ProbeResponse;
import com.maveric.submersible.model.Probe;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/probe")
public class ProbeController {

    @PostMapping("/execute")
    public ProbeResponse executeCommands(@RequestBody CommandRequest request) {
        Probe probe = new Probe(
                request.getStart(),
                request.getDirection(),
                request.getGridWidth(),
                request.getGridHeight(),
                request.getObstacles()
        );

        probe.execute(request.getCommands());

        return new ProbeResponse(
                probe.getPosition(),
                probe.getDirection(),
                probe.getVisited()
        );
    }
}
