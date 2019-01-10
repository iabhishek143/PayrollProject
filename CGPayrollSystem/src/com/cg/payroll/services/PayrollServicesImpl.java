package com.cg.payroll.services;

import java.util.List;
import java.util.Scanner;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.daoservices.AssociateDAOImpl;
import com.cg.payroll.exceptions.AssociateDetailsNotFoundException;

public class PayrollServicesImpl implements PayrollServices{
	Scanner scanner= new Scanner( System.in);
	private AssociateDAO associateDAO;

	public PayrollServicesImpl() {
		associateDAO=new AssociateDAOImpl();
	}
	public PayrollServicesImpl(AssociateDAO associateDAO) {
		super();
		this.associateDAO= associateDAO;
	}

	@Override
	public int acceptAssociateDetails(String firstName, String lastName, String department, String designation,
			String pancard, String emailId, int yearlyInvestmentUnder80C, int basicSalary, int epf, int companyPf, int accountNumber, String bankName, String ifscCode) {

		Associate associate= new Associate(yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId,new Salary(basicSalary, epf, companyPf), new BankDetails(accountNumber, bankName, ifscCode));
		associate = associateDAO.save(associate);
		return associate.getAssociateID();
	} 

	@Override
	public int calculateNetSalary(int associateID) throws AssociateDetailsNotFoundException {
		Associate associate= this.getAssociateDetails(associateID);
		int basicSalary, hra, conveyanceAllowance, personalAllowance, otherAllowance, grossSalary, annualSalary, taxableAmmount=0;

		basicSalary = associate.getSalary().getBasicSalary();
		hra = basicSalary * 40/100;
		conveyanceAllowance = basicSalary * 30/100;
		otherAllowance=basicSalary * 20/100; 
		personalAllowance=basicSalary * 20/100;
		grossSalary = basicSalary+ hra+ conveyanceAllowance+ otherAllowance+ personalAllowance;
		annualSalary= grossSalary* 12;

		if(associate.getYearlyInvestmentUnder80C()+associate.getSalary().getCompanyPf()+associate.getSalary().getEpf()<180000)
			taxableAmmount= annualSalary- associate.getYearlyInvestmentUnder80C()- associate.getSalary().getEpf()*12 - associate.getSalary().getCompanyPf()*12; 
		else 
			taxableAmmount= annualSalary- associate.getSalary().getEpf()*12 - associate.getSalary().getCompanyPf()*12- 180000;


		if(annualSalary<=250000) {
			associate.getSalary().setNetSalary(grossSalary- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf());
			associateDAO.update(associate);
			return grossSalary- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf();
		}


		else if(annualSalary>250000 && taxableAmmount<= 500000) {
			taxableAmmount-=250000;
			int monthlyTax= (taxableAmmount/10)/12;
			associate.getSalary().setNetSalary(grossSalary- monthlyTax- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf());
			associateDAO.update(associate);
			return grossSalary- monthlyTax- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf();
		}

		else if(annualSalary>500000 && annualSalary<=1000000) {
			taxableAmmount-=500000;
			int monthlyTax= (taxableAmmount/5)/12+ 25000;	
			associate.getSalary().setNetSalary(grossSalary- monthlyTax- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf());
			associateDAO.update(associate);
			return grossSalary-monthlyTax- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf();
		}

		else if(annualSalary> 1000000) {
			taxableAmmount-= 1000000;
			int monthlyTax=((taxableAmmount*30)/100 +125000)/12;
			associate.getSalary().setNetSalary(grossSalary- monthlyTax- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf());
			associateDAO.update(associate);
			return grossSalary-monthlyTax- associate.getSalary().getEpf()- associate.getSalary().getCompanyPf();
		}
		return 0;
	}

	@Override
	public Associate getAssociateDetails(int associateID) throws AssociateDetailsNotFoundException {
		Associate associate= associateDAO.findOne(associateID);
		if(associate== null)
			throw new AssociateDetailsNotFoundException("Associate "+associateID+" is not found in database.");
		return associate;
	}

	@Override
	public List<Associate> getAllAssociateDetails() {

		return associateDAO.findAll();
	}



}

