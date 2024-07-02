package com.team3.springProject.reservationTable;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.team3.springProject.classTable.ClassTable;
import com.team3.springProject.userTable.UserTable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReservationTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserTable userTable;

	@ManyToOne
	@JoinColumn(name = "classTable_id", referencedColumnName = "id", nullable = false)
	private ClassTable classTable;

	private LocalDate reservationDay;

	private int count;

	private String time;

	private String state;

	private LocalDateTime updateAt;

	private LocalDateTime createAt;

}
