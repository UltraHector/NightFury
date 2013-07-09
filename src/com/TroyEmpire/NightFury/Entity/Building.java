package com.TroyEmpire.NightFury.Entity;

import java.io.Serializable;

import lombok.Data;


@Data
public class Building implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private double latitude;
	private double longitude;
	private String description;
	private int pathDotId;
	
	//the scale of the building
	private double miniLatitude;
	private double maxLatitude;
	private double miniLongitude;
	private double maxLongitude;
}
