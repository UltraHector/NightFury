package com.TroyEmpire.NightFury.Ghost.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Ghost.IService.IInitiateDataService;
import com.TroyEmpire.NightFury.Util.Util;

import android.os.Environment;
import android.util.Log;

public class InitiateDataService implements IInitiateDataService {

	@Override
	public boolean initiateRestaurantData(int campusId) {
		// get the absolute path in the SD card
		String absolutePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		final String sourceUrl = Constant.NIGHTFURY_SERVER_URL
				+ "/downloadData/restaurants/" + campusId;
		final String destFolderPath = absolutePath + "/"
				+ Constant.RESTAURANT_DATA_PATH;
		Thread child = new Thread() {
			public void run() {
				downloadZipData(sourceUrl, destFolderPath);
			}
		};
		child.start();
		try {
			child.join();
			if (Util.unzipFile(destFolderPath + "/temp.zip", destFolderPath) == false)
				return false;
			new File(destFolderPath + "/temp.zip").delete();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean initiateMapData(int campusId) {
		// get the absolute path in the SD card
		String absolutePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		final String sourceUrl = Constant.NIGHTFURY_SERVER_URL
				+ "/downloadData/map/" + campusId;
		final String destFolderPath = absolutePath + "/"
				+ Constant.MAP_DATA_PATH;
		Thread child = new Thread() {
			public void run() {
				downloadZipData(sourceUrl, destFolderPath);
			}
		};
		child.start();
		try {
			child.join();
			if (Util.unzipFile(destFolderPath + "/temp.zip",
					destFolderPath) == false)
				return false;
			new File(destFolderPath + "/temp.zip").delete();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void cleanLegacyData(String dataFolder) throws IOException {
		FileUtils.deleteDirectory(new File(dataFolder));
	}

	private boolean downloadZipData(String sourceUrl, String destFolderPath) {

		String destZipFilePath = destFolderPath + "/temp.zip";
		try {
			cleanLegacyData(destFolderPath);
			// connect to server, get response
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(sourceUrl);
			InputStream in = client.execute(request).getEntity().getContent();
			// set the data files
			File dataFolder = new File(destFolderPath);
			File destZipFile = new File(destZipFilePath);
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			if (destZipFile.createNewFile() == false)
				return false;
			OutputStream out = new FileOutputStream(destZipFile);
			// save the zip file
			IOUtils.copy(in, out);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
