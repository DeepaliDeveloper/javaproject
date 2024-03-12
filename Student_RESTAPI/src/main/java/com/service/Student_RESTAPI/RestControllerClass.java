package com.service.Student_RESTAPI;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import com.models.StudentEntity;
import com.models.StudentProcessingClass;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RestController
@ComponentScan("com.models")
@RequestMapping("/api")
public class RestControllerClass {
	
	@Autowired
	StudentProcessingClass obj;
	
	String status = "";

	@GetMapping("/msg")
	@Produces(MediaType.TEXT_PLAIN)
	public String msg() {
		return "welcome to Student Spring boot project";
	}

	@GetMapping("/getrecord/{rollno}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<StudentEntity> serviceForGetRecord(@PathVariable int rollno) {

		ArrayList<StudentEntity> lis = new ArrayList<StudentEntity>();
		lis = obj.getRecord(rollno);

		return lis;
	}

	@DeleteMapping("/deleterecord/{rollno}")
	@Produces(MediaType.TEXT_PLAIN)
	public String serviceForDeleteRecord(@PathVariable int rollno) {

		status = obj.deleteRecord(rollno);

		return status;
	}

	@PutMapping("/updatarecord")
	@Produces(MediaType.TEXT_PLAIN)
	public String serviceForUpdateRecord(@FormParam("rollno") int rollno, @FormParam("city") String city) {

		status = obj.updateRecord(rollno, city);

		return status;
	}

	@PostMapping("/newrecord")
	@Produces(MediaType.TEXT_PLAIN)
	public String serviceForInsertNewRecord(@FormParam("rollno") int rollno, @FormParam("city") String city,
			@FormParam("sname") String sname, @FormParam("state") String state) {
		
		StudentEntity ac = new StudentEntity();
		ac.setRollno(rollno);
		ac.setSname(sname);
		ac.setCity(city);
		ac.setState(state);

		status = obj.insertNewRecord(ac);

		return status;
	}
}
