package com.team3.springProject.reservationTable;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/reservation")
public class ReservationController {

	private static final Logger logger = LogManager.getLogger(ReservationController.class);

	private final ReservationService reservationService;

	@GetMapping("/list")
	public String reservationList(Model model, Principal principal) {
		String userId = principal.getName();
		List<ReservationTable> reservationList = reservationService.getReservationByUserIdSortedByCreateAtDesc(userId);
		model.addAttribute("reservationList", reservationList);
		return "reservation_list";
	}

	// 예약 생성 폼을 보여주는 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String showCreateReservationForm(Model model, Principal principal) {
		logger.debug("showCreateReservationForm 메서드 진입");

		if (principal == null) {
			logger.debug("사용자가 인증되지 않음. 로그인 페이지로 리다이렉트");
			return "redirect:/user/login";
		}

		return "reservation_detail";
	}

	// 예약을 생성하는 메소드
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String createReservation(@RequestParam("className") String className, @RequestParam("date") String date,
			@RequestParam("time") String time, @RequestParam("count") int count, Principal principal,
			RedirectAttributes redirectAttributes) {
		if (principal == null) {
			return "redirect:/user/login";
		}

		String userId = principal.getName();
		reservationService.createReservation(userId, className, LocalDate.parse(date), time, count);
		redirectAttributes.addFlashAttribute("alertMessage", "예약이 성공적으로 생성되었습니다. 10분이내로 결제를 진행하지 않으면 예약이 취소 됩니다.");

		return "redirect:/user/reservation/list";
	}

	// 예약 리스트 삭제
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<String> deleteReservation(@PathVariable("id") Long id, Principal principal) {
		try {
			reservationService.deleteReservation(id);
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("error");
		}
	}

}
