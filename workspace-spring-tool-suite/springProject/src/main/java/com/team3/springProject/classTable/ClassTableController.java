package com.team3.springProject.classTable;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ClassTableController {

   private final ClassTableService classTableService;

   @GetMapping("/reservation")
   public String showReservationPage(Model model) {
      List<ClassTable> classList = classTableService.getClassTableList();
      model.addAttribute("classList", classList);
      return "reservation_form";
   }

   @PreAuthorize("isAuthenticated()")
   @GetMapping("/reservation/detail")
   public String showReservationDetail(@RequestParam("name") String className, Model model) {
      ClassTable classItem = classTableService.getClassByName(className);
      model.addAttribute("classItem", classItem);
      return "reservation_detail";
   }

}
