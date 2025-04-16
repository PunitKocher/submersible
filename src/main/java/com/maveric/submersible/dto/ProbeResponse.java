package com.maveric.submersible.dto;

import com.maveric.submersible.model.Direction;
import com.maveric.submersible.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProbeResponse {
    private Position current;
    private Direction direction;
    private Set<Position> visited;
}
