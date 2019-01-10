package com.cg.payroll.client;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.DoubleSupplier;

import com.cg.payroll.beans.*;
import com.cg.payroll.exceptions.AssociateDetailsNotFoundException;
import com.cg.payroll.serialization.ObjectSerialization;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;

public class MainClass {

	public static void main(String[] args) throws AssociateDetailsNotFoundException {
		PayrollServices payrollService=  new PayrollServicesImpl();

		int associateID= payrollService.acceptAssociateDetails("Abhishek", "Sharma", "Development", "Sr Consultant", "CVEP4543p", "abhishek@gmail.com", 5000, 50000, 1000, 1000, 11111111, "Kotak", "CVEP4501H");

		System.out.println(associateID);

		List<Associate> associates= payrollService.getAllAssociateDetails();
		for (Associate associate : associates) {
			if (associate != null) {
				System.out.println(payrollService.getAssociateDetails(associate.getAssociateID()));
			}
		}
		int associateId= 0;
		Scanner scanner= new Scanner(System.in);
		int ch = 0;
		System.out.println("\n\n>>>Associate Management System<<<");
		do {
			System.out.println(
					"___________________\n Please enter ur choice : \n1. Add associate \n2. Find associate \n3. Calculate salary \n4. Display all assocciates\n5. Exit\n___________________");
			ch = scanner.nextInt();
			scanner.nextLine();
			switch (ch) {
			case 1:
				System.out.println("Enter new associate details : ");
				System.out.println("First Name : ");
				String firstName = scanner.nextLine();
				System.out.println("Last Name : ");
				String lastName = scanner.nextLine();
				System.out.println("Department : ");
				String department = scanner.nextLine();
				System.out.println("Designation : ");
				String designation = scanner.nextLine();
				System.out.println("Pancard no. : ");
				String pancard = scanner.nextLine();
				System.out.println("Email Id : ");
				String emailId = scanner.nextLine();
				System.out.println("Basic Salary : ");
				int basicSalary = scanner.nextInt();
				System.out.println("EPF Amount : ");
				int epf = scanner.nextInt();
				System.out.println("Company PF Amount :");
				int companyPf = scanner.nextInt();
				System.out.println("Yearly Investment Under 80C : ");
				int yearlyInvestmentUnder80C = scanner.nextInt();
				System.out.println("Bank Account no. :");
				int accountNumber = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Bank Name : ");
				String bankName = scanner.nextLine();
				System.out.println("Bank IFSC Code : ");
				String ifscCode = scanner.nextLine();
				associateId = payrollService.acceptAssociateDetails( firstName, lastName, department,
						designation, pancard, emailId, yearlyInvestmentUnder80C, basicSalary, epf, companyPf, accountNumber, bankName, ifscCode);
				System.out.println(associateId);
				break;

			case 2:
				System.out.println("Enter Associate Id to search");
				associateId = scanner.nextInt();
				try {
					System.out.println(
							"Associate details : \n" + payrollService.getAssociateDetails(associateId).toString());
				} catch (AssociateDetailsNotFoundException e) {
					e.printStackTrace();
				}
				break;

			case 3:
				System.out.println("Enter Associate Id to search");
				associateId = scanner.nextInt();
				try {
					System.out.println("Associate salary : " + payrollService.calculateNetSalary(associateId));
				} catch (AssociateDetailsNotFoundException e) {
					e.printStackTrace();
				}
				break;

			case 4:
				System.out.println("All Associate Details");
				List<Associate> associates1 = payrollService.getAllAssociateDetails();
				try {
					for (Associate associate : associates1)
						if (associate != null)
							System.out.println("___________________________________________\n"
									+ payrollService.getAssociateDetails(associate.getAssociateID()).toString());
				} catch (AssociateDetailsNotFoundException e) {
					e.printStackTrace();
				}
				break;

			case 5:
				System.out.println("Exited");
				System.exit(0);

			default:
				System.out.println("Invalid Choice...");
				break;
			}
		} while (ch != 5);
		File file= new File("D:\\PayrollAssociateData.txt");
		try {
			ObjectSerialization.doSerialization(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
