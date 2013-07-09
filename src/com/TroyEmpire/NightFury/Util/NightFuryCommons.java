package com.TroyEmpire.NightFury.Util;

import java.io.IOException;

import org.jsoup.Jsoup;

import com.TroyEmpire.NightFury.Constant.Constant;

public class NightFuryCommons {
	public static boolean checkWhetherCouldConnectToServer() {
		try {
			if (Jsoup
					.connect(
							Constant.NIGHTFURY_SERVER_URL
									+ Constant.NIGHTFURY_SERVER_CONNECTION_CHECK_SUB_URL)
					.execute()
					.hasHeader(Constant.NIGHTFURY_SERVER_CONNECTION_STATUS))
				return true;
			else
				return false;
		} catch (IOException e) {
			return false;
		}
	}
}
