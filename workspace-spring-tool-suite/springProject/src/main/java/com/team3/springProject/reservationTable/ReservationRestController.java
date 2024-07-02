package com.team3.springProject.reservationTable;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team3.springProject.classTable.ClassTable;
import com.team3.springProject.classTable.ClassTableService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReservationRestController {

	private final ReservationService reservationService;
	private final ClassTableService classTableService;
	private static final Logger logger = LogManager.getLogger(ReservationController.class);

	// 예약 가능 인원 체크
	@GetMapping("/availableCount")
	@ResponseBody // JSON 응답을 위한 어노테이션
	public AvailableCountResponse getAvailableCount(@RequestParam("className") String className,
			@RequestParam("date") String dateString, @RequestParam("time") String time) {

		LocalDate reservationDay = LocalDate.parse(dateString);
		ClassTable classTable = classTableService.getClassByName(className);
		int reservatedCount = reservationService.getTotalReservedCount(classTable, reservationDay, time);
		int availableCount = classTable.getMaxCapacity() - reservatedCount;
		return new AvailableCountResponse(availableCount);
	}
	
	@GetMapping("/completeReservation")
	public ResponseEntity<?> completeReservation(@RequestParam("reservationId") Long reservationId,
			@RequestParam("state") String state) {
		
		try {
			reservationService.completeReservation(reservationId, state);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
