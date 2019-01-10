package com.cg.payroll.daoservices;

import java.util.List;

import com.cg.payroll.beans.Associate;

public interface AssociateDAO {

	public Associate save(Associate associate);
	Associate update(Associate associate);
	Associate findOne(int associateID);
	List<Associate> findAll();



}
