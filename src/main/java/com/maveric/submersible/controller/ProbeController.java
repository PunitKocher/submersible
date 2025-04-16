package com.maveric.submersible.controller;

import com.maveric.submersible.dto.CommandRequest;
import com.maveric.submersible.dto.ProbeResponse;
import com.maveric.submersible.model.Probe;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/probe")
public class ProbeController {

    @PostMapping("/execute")
    public ProbeResponse executeCommands(@Valid @RequestBody CommandRequest commandRequest) {
        Probe probe = new Probe(
                commandRequest.start(),
                commandRequest.direction(),
                commandRequest.gridWidth(),
                commandRequest.gridHeight(),
                commandRequest.obstacles()
        );

        probe.execute(commandRequest.commands());

        return new ProbeResponse(
                probe.getPosition(),
                probe.getDirection(),
                probe.getVisited()
        );
    }
}
