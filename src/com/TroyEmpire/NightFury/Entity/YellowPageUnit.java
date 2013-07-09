package com.TroyEmpire.NightFury.Entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class YellowPageUnit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	String name;
	String phoneNumber;
	String managerName;
	String description;

}
