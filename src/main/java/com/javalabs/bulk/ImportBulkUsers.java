package com.javalabs.bulk;

import static com.javalabs.util.CodeUtility.getDateCellValue;
import static com.javalabs.util.CodeUtility.getStringCellValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.javalabs.dto.CreateUserRequest;

public class ImportBulkUsers {

	public static List<CreateUserRequest> importBulkUsersFromExcel() {
		String excelFilePath = "path/to/your/excel/file.xlsx";
		List<CreateUserRequest> createUserRequests = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
				Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue;
				}
				Cell firstNameCell = row.getCell(0);
				Cell lastNameCell = row.getCell(1);
				Cell usernameCell = row.getCell(2);
				Cell passwordCell = row.getCell(3);
				Cell emailCell = row.getCell(4);
				Cell contactCell = row.getCell(5);
				Cell dateOfJoiningCell = row.getCell(6);
				Cell projectCell = row.getCell(7);

				String firstName = getStringCellValue(firstNameCell);
				String lastName = getStringCellValue(lastNameCell);
				String username = getStringCellValue(usernameCell);
				String password = getStringCellValue(passwordCell);
				String email = getStringCellValue(emailCell);
				String contact = getStringCellValue(contactCell);
				LocalDate dateOfJoining = getDateCellValue(dateOfJoiningCell);
				String project = getStringCellValue(projectCell);

				CreateUserRequest createUserRequest = CreateUserRequest.builder().contact(contact)
						.dateOfJoining(dateOfJoining).email(email).firstName(firstName)
						.lastName(lastName).password(password).project(Arrays.asList(project.split(",")))
						.username(username).build();
				createUserRequests.add(createUserRequest);
				System.out.println("createUserRequest : "+createUserRequest);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return createUserRequests;
	}

	
}
