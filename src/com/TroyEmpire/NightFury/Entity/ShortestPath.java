package com.TroyEmpire.NightFury.Entity;

import lombok.Data;

@Data
public class ShortestPath {
	private long id;
	private long sourceId;
	private long destId;
	private String shortestPath;
}
