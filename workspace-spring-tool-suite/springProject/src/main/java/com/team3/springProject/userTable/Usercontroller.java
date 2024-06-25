package com.team3.springProject.userTable;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class Usercontroller {

	private final UserService userService;

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		try {
			userService.create(userCreateForm.getLoginId(), userCreateForm.getPassword1(), userCreateForm.getName(),
					userCreateForm.getPhoneNumber(), userCreateForm.getGender());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		return "redirect:/user/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}

	@GetMapping("/mypage/{loginId}")
	public String mypage(Model model, @PathVariable("loginId") String loginId) {
		UserTable userTable = this.userService.getUser(loginId);
		model.addAttribute("userTable", userTable);
		return "mypage_form";
	}
	@PostMapping("/mypage/{loginId}")
	public String mypageUpdate(@ModelAttribute("userTable") @Valid UserTable userTable, BindingResult bindingResult,
							   @RequestParam(required = false) String newpassword1,
							   @RequestParam(required = false) String newpassword2,
							   @PathVariable("loginId") String loginId, Model model) throws IOException {
		if (bindingResult.hasErrors()) {
			return "mypage_form";
		}

		UserTable existingUser = userService.getUser(loginId);

		if (existingUser == null) {
			return "error"; // Handle case where user is not found
		}

		if (newpassword1 != null && !newpassword1.isEmpty()) {
			if (!newpassword1.equals(newpassword2)) {
				bindingResult.reject("passwordMismatch", "새 비밀번호가 일치하지 않습니다.");
				return "redirect:/user/mypage/" + loginId;
			}
			userService.updateUser(existingUser, newpassword1, userTable.getName(), userTable.getPhoneNumber(), userTable.getGender());
		} else {
			userService.updateUser(existingUser, null, userTable.getName(), userTable.getPhoneNumber(), userTable.getGender());
		}

//		return "redirect:/user/mypage/" + loginId;
		return String.format("redirect:/weather", loginId);
	}
}
	