package com.example.springbatchdemo.service;

import static com.example.springbatchdemo.domain.entity.type.SkillStackType.findBySkillStackType;

import com.example.springbatchdemo.domain.entity.type.SkillStackType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchService {

	public void addData(String keyword){

		SkillStackType existingSkillStack = findBySkillStackType(keyword);
		LocalDateTime currentDateTime = LocalDateTime.now();

		String csvFilePath = getCSVFilePath(currentDateTime);
		if (existingSkillStack != null) {
			write(csvFilePath, keyword, currentDateTime, existingSkillStack.getSkillStackCode());
		} else {
			write(csvFilePath, keyword, currentDateTime, -1L);
		}

	}

	public static String getCSVFilePath(LocalDateTime currentDateTime){

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH");

		String year = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy"));
		String month = currentDateTime.format(DateTimeFormatter.ofPattern("MM"));
		String day = currentDateTime.format(DateTimeFormatter.ofPattern("dd"));
		String hour = currentDateTime.format(DateTimeFormatter.ofPattern("HH"));

		String folderPath = "src/main/resources" + "/" + year + "/" + month + "/" + day;
		File folder = new File(folderPath);
		boolean success = folder.mkdirs();

		return folderPath + "/" + hour + ".csv";
	}

	public static void write(String csvFilePath,  String keyword, LocalDateTime currentDateTime, Long skillStackCode){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = currentDateTime.format(formatter);
		try (Writer writer = new FileWriter(csvFilePath, true);
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

			csvPrinter.printRecord(keyword, formattedDateTime, skillStackCode);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
