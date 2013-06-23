package com.TroyEmpire.NightFury.Entity;

import lombok.Data;


@Data
public class Cell{
	private long id;
	private String name;
	// foreign key to reference building which the name belongs to
	private long buildingId;
}
