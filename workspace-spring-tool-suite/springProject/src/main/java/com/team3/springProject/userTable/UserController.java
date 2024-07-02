package com.team3.springProject.userTable;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

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
         bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
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

   @GetMapping("/signup/check-loginId")
   @ResponseBody
   public String checkLoginId(@RequestParam("loginId") String loginId) {
        try {
           if(!loginId.matches("^[a-zA-Z0-9]{6,12}$")) {
              return "아이디는 영문, 숫자를 조합하여 6~12자로 입력해주세요.";
           }
           
            boolean isDuplicate = userService.isLoginIdDuplicate(loginId);
            return isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
        } catch (Exception e) {
            e.printStackTrace(); //오류 로그 출력
            return "서버 오류가 발생했습니다.";
        }
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
      public String mypageUpdate(
              @ModelAttribute("userTable") @Valid UserTable userTable, BindingResult bindingResult,
              @RequestParam(name = "currentPassword", required = true) String currentPassword,
              @RequestParam(name = "newpassword1", required = false) String newpassword1,
              @RequestParam(name = "newpassword2", required = false) String newpassword2,
              @PathVariable("loginId") String loginId, Model model) throws IOException {
	   	  
	   	  // 폼 데이터 유효성 검사
          if (bindingResult.hasErrors()) {
              return "mypage_form"; //오류 있으면 다시 폼으로
          }

          // 현재 비밀번호가 입력되지 않았을 경우 오류메세지
          if (currentPassword == null || currentPassword.isEmpty()) {
              bindingResult.reject("currentPasswordMissing", "현재 비밀번호를 입력해주세요.");
              return "mypage_form";
          }
          
          // loginId 기반으로 현재 사용자 정보 get
          UserTable existingUser = userService.getUser(loginId);
          
          // 사용자가 존재하지 않으면 에러 페이지로
          if (existingUser == null) {
              return "error"; // Handle case where user is not found
          }

          // 현재 비밀번호가 일치하지 않을 경우 오류메세지
          if (!userService.checkPassword(existingUser, currentPassword)) {
              bindingResult.reject("currentPasswordIncorrect", "현재 비밀번호가 일치하지 않습니다.");
              return "mypage_form";
          }
          
          
          // 새 비밀번호가 입력된 경우 pw1,2 일치하는 지 확인
          if (newpassword1 != null && !newpassword1.isEmpty()) {
              if (!newpassword1.equals(newpassword2)) {
                  bindingResult.reject("passwordMismatch", "새 비밀번호가 일치하지 않습니다.");
                  return "mypage_form";
              }
              // 새 비번 포함 사용자 정보 업데이트
              userService.updateUser(existingUser, newpassword1, userTable.getName(), userTable.getPhoneNumber(), userTable.getGender());
          } else {
        	  // 새 비번 없으면 비번 제외 나머지 정보 업데이트
              userService.updateUser(existingUser, null, userTable.getName(), userTable.getPhoneNumber(), userTable.getGender());
          }
          
          // 성공 메시지를 모델에 추가
          model.addAttribute("successMessage", "회원 정보 수정이 정상적으로 처리됐습니다!");

          return String.format("redirect:/user/logout?logoutSuccess=true");
      }
   
   @PostMapping("/delete")
   public String deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
       String loginId = userDetails.getUsername(); // 현재 로그인된 사용자의 ID 가져오기
       userService.deleteUser(loginId);
       return "redirect:/user/logout"; // 탈퇴 후 로그아웃 처리
   }

}