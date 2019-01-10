package com.cg.payroll.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.cg.payroll.beans.Associate;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.daoservices.AssociateDAOImpl;
import com.cg.payroll.util.PayrollUtil;

public class ObjectSerialization {
	public static void doSerialization(File file) throws IOException, FileNotFoundException {
		
		
		for (Associate associate : PayrollUtil.associates.values()) {
			try(ObjectOutputStream destWriter= new ObjectOutputStream(new FileOutputStream(file))){
				destWriter.writeObject(associate);
			}
		}
	}
}

