package com.TroyEmpire.NightFury.Entity;

import lombok.Data;

@Data
public class Restaurant {
	private long id;
	private String phoneNumber;
	private String description;
	private String managerName;
	private String transporterName;
	private String name;
	// Chinese restaurant or Western
	private String type;
	// whether the restaurant has been bookmarked
	private int bookmarked;
	// minimum order cost for free delivery 
	private int minimumOrder;
	// deliver time in minutes
	private int deliverTime;
}
