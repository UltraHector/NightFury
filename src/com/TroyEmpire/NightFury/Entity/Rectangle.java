package com.TroyEmpire.NightFury.Entity;

import lombok.Data;

@Data
public class Rectangle {

	/**
	 * 左上角和右下角的点表示矩形
	 */
	private LocationPosition leftTop, rightBottom;

}
