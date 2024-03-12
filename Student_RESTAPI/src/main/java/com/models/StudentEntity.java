package com.models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="student")
public class StudentEntity {
	
	@Id
	@Column(name="rollno")
	private int rollno;
	
	@Column(name="sname")
	private String sname;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
}
