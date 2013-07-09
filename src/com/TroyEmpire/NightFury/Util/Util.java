package com.TroyEmpire.NightFury.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;

import com.TroyEmpire.NightFury.Enum.WeekDay;

public class Util {

	public static final SimpleDateFormat DATA_FORMATER = new SimpleDateFormat("yyyyMMddHHmmss",
			Locale.CHINA);
	/**
	 * @param sourceFile the filename of zip file including the absolute path
	 * @param destFileFolder the folder in which the zipped files will be put in
	 */
	public static boolean unzipFile(String sourceFile, String destFileFolder) {
		try{
			File zipFile = new File(destFileFolder);
			if(!zipFile.exists()) zipFile.mkdirs();
			ZipInputStream zipFileStream = new ZipInputStream(new FileInputStream(sourceFile));
			ZipEntry entry = zipFileStream.getNextEntry();
			while(entry != null){
				String fileName = entry.getName();
				File newFile = new File(destFileFolder + File.separator + fileName);
				if(entry.isDirectory()) 
					new File(newFile.getParent()).mkdirs();
				else{
					new File(newFile.getParent()).mkdirs();
					FileOutputStream out = new FileOutputStream(newFile);
					IOUtils.copy(zipFileStream, out);
				}
				entry = zipFileStream.getNextEntry();
			}
			zipFileStream.closeEntry();	
			
		}catch(Exception e){
			return false;
		}	
		return true;
	}
	
	
	public static int getWeekdayInt() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	public static WeekDay getInt2Weekday(int weekdayFrom){
		switch (weekdayFrom) {
		case 0:
			return WeekDay.周日;
		case 1:
			return WeekDay.周一;
		case 2:
			return WeekDay.周二;
		case 3:
			return WeekDay.周三;
		case 4:
			return WeekDay.周四;
		case 5:
			return WeekDay.周五;
		case 6:
			return WeekDay.周六;
		}
		return WeekDay.周一;
	}
	
	public static WeekDay getWeekday(){
		int weekdayFrom = getWeekdayInt();
		switch (weekdayFrom) {
		case 0:
			return WeekDay.周日;
		case 1:
			return WeekDay.周一;
		case 2:
			return WeekDay.周二;
		case 3:
			return WeekDay.周三;
		case 4:
			return WeekDay.周四;
		case 5:
			return WeekDay.周五;
		case 6:
			return WeekDay.周六;
		}
		return WeekDay.周一;
	}
	

}
