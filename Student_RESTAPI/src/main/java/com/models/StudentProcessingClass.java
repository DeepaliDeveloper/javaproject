package com.models;

import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.*;

@Component
public class StudentProcessingClass {

	Configuration cfg;
	SessionFactory sf;
	Session ses;
	
	public StudentProcessingClass() {
		try {
			cfg = new Configuration().configure();
			sf = cfg.addAnnotatedClass(StudentEntity.class).buildSessionFactory();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<StudentEntity> getRecord(int rollno) {

		ArrayList<StudentEntity> list = new ArrayList<StudentEntity>();
		StudentEntity ac;
		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			CriteriaBuilder cb = ses.getCriteriaBuilder();
			CriteriaQuery<StudentEntity> qry = cb.createQuery(StudentEntity.class);

			Root<StudentEntity> root = qry.from(StudentEntity.class);
			qry.select(root).where(cb.equal(root.get("rollno"), rollno));

			List<StudentEntity> resultList = ses.createQuery(qry).getResultList();
			if (resultList.size() > 0) {
				ac = new StudentEntity();

				for (int i = 0; i < resultList.size(); i++) {
					StudentEntity ae = (StudentEntity) resultList.get(i);
					ac.setRollno(ae.getRollno());
					ac.setSname(ae.getSname());
					ac.setCity(ae.getCity());
					ac.setState(ae.getState());
				}
			} else {
				ac = new StudentEntity();
				ac.setRollno(0);
				ac.setSname("not valid");
				ac.setCity("not valid");
				ac.setState("not valid");
			}

		} catch (Exception e) {
			ac = new StudentEntity();
			ac.setRollno(0);
			ac.setSname("error occured");
			ac.setCity("error occured");
			ac.setState("error occured");
			e.printStackTrace();
		}

		ses.getTransaction().commit();
		ses.close();
		list.add(ac);

		return list;
	}

	public String deleteRecord(int rollno) {

		String status = "";
		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			CriteriaBuilder cb = ses.getCriteriaBuilder();
			CriteriaDelete<StudentEntity> cdelete = cb.createCriteriaDelete(StudentEntity.class);

			Root<StudentEntity> root = cdelete.from(StudentEntity.class);
			cdelete.where(cb.equal(root.get("rollno"), rollno));

			int result = ses.createQuery(cdelete).executeUpdate();

			ses.getTransaction().commit();
			ses.close();

			if (result > 0)
				status = "Successfully deleted";
			else
				status = "Not deleted";

		} catch (Exception e) {
			status = "error occured.";
			System.out.println(e);
		}

		return status;
	}

	public String updateRecord(int rollno, String city) {

		String status = "not valid";

		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			CriteriaBuilder cb = ses.getCriteriaBuilder();
			CriteriaUpdate<StudentEntity> cupdate = cb.createCriteriaUpdate(StudentEntity.class);

			Root<StudentEntity> root = cupdate.from(StudentEntity.class);
			cupdate.set("city", city);
			cupdate.where(cb.equal(root.get("rollno"), rollno));

			int result = ses.createQuery(cupdate).executeUpdate();

			ses.getTransaction().commit();
			ses.close();

			if (result > 0)
				status = " city updated successfully";
			else
				status = "Updation failed";

		} catch (Exception e) {
			status = "error occured.";
			e.printStackTrace();
		}

		return status;
	}

	public String insertNewRecord(StudentEntity ae) {

		String status = "not valid";
		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			ses.save(ae);
			ses.getTransaction().commit();
			ses.close();

			status = "Data saved..";

		} catch (Exception e) {
			status = "error occured.";
			e.printStackTrace();
		}

		return status;
	}
}
