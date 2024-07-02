package com.team3.springProject.classTable;

import java.util.List;

import com.team3.springProject.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClassTableService {
   
   private final ClassTableRepository classTableRepository;

   public List<ClassTable> getClassTableList() {
      return this.classTableRepository.findAll();
   }

   @Transactional
   public ClassTable getClassByName(String className) {
      return classTableRepository.findByName(className)
              .orElseThrow(() -> new DataNotFoundException("Class not found with name: " + className));
   }
   
   @Transactional
   public void saveClassTable(ClassTable classTable) {
       classTableRepository.save(classTable);
   }
}
