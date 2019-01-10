package com.cg.payroll.daoservices;

import java.util.ArrayList;
import java.util.List;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.util.PayrollUtil;

public class AssociateDAOImpl implements AssociateDAO {


	@Override
	public  Associate save(Associate associate) {
		associate.setAssociateID(PayrollUtil.getASSOCIATE_ID_COUNTER());
		PayrollUtil.associates.put(associate.getAssociateID(),associate);
		return associate;
	}

	@Override
	public Associate update(Associate associate) {
		return PayrollUtil.associates.put(associate.getAssociateID(), associate);
	}

	@Override
	public Associate findOne(int associateID) {
		return PayrollUtil.associates.get(associateID);
	}

	@Override
	public List<Associate> findAll() {
		return new ArrayList<>(PayrollUtil.associates.values());
	}

}
