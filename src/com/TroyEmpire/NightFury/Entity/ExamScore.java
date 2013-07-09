package com.TroyEmpire.NightFury.Entity;


import java.io.Serializable;

import lombok.Data;

@Data
public class ExamScore implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courceName;
	private String creditPoints;
	private String score;
	private String status;
}
