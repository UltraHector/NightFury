package com.TroyEmpire.NightFury.Entity;

import java.util.Date;

import com.TroyEmpire.NightFury.Enum.NewsType;

import lombok.Data;

@Data
public class NewsOnServer {
	private int id;
	private Date publishDate;
	private String title;
	private String content;
	private NewsType newsType;
}
