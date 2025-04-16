package com.maveric.submersible.dto;

import com.maveric.submersible.model.Direction;
import com.maveric.submersible.model.Position;

import java.util.Set;

public record ProbeResponse(Position current, Direction direction, Set<Position> visited) {}
