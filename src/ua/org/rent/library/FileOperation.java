/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import android.os.Environment;
import android.content.Context;

/**
 *
 * @author petroff
 */
public class FileOperation {

	final String LOG_TAG = "myLogs";
	String FILENAME = "data";
	final String DIR_SD = "currencyonline";
	final String FILENAME_SD = "dataSD";
	Context context;

	FileOperation(Context context) {
		this.context = context;
	}
	
	void setFileName(String fileName){
		this.FILENAME = fileName;
	}

	void writeFile(String data) {
		try {
			// отрываем поток для записи
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					context.openFileOutput(FILENAME, context.MODE_PRIVATE)));
			// пишем данные
			bw.write(data);
			// закрываем поток
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String readFile() {
		String res = "";
		try {
			// открываем поток для чтения
			BufferedReader br = new BufferedReader(new InputStreamReader(
					context.openFileInput(FILENAME)));
			String str = "";
			while ((str = br.readLine()) != null) {
				res+= str;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	void writeFileSD(String data) {
		// проверяем доступность SD
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return;
		}
		// получаем путь к SD
		File sdPath = Environment.getExternalStorageDirectory();
		// добавляем свой каталог к пути
		sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
		// создаем каталог
		sdPath.mkdirs();
		// формируем объект File, который содержит путь к файлу
		File sdFile = new File(sdPath, FILENAME_SD);
		try {
			// открываем поток для записи
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			// пишем данные
			bw.write(data);
			// закрываем поток
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void readFileSD() {
		// проверяем доступность SD
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return;
		}
		// получаем путь к SD
		File sdPath = Environment.getExternalStorageDirectory();
		// добавляем свой каталог к пути
		sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
		// формируем объект File, который содержит путь к файлу
		File sdFile = new File(sdPath, FILENAME_SD);
		try {
			// открываем поток для чтения
			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			String str = "";
			// читаем содержимое
			while ((str = br.readLine()) != null) {
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
