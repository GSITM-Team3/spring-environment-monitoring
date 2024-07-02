package com.team3.springProject.reservationTable;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team3.springProject.classTable.ClassTable;
import com.team3.springProject.userTable.UserTable;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationTable, Long> {
   List<ReservationTable> findByUserTable(UserTable userTable);

   List<ReservationTable> findByUserTableOrderByCreateAtDesc(UserTable userTable);

   List<ReservationTable> findByClassTableAndReservationDayAndTime(ClassTable classTable, LocalDate reservationDay,
         String time);

   void deleteByUserTable(UserTable userTable);
}
