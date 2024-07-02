package com.team3.springProject.reservationTable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.team3.springProject.classTable.ClassTable;
import com.team3.springProject.classTable.ClassTableService;
import com.team3.springProject.userTable.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team3.springProject.userTable.UserRepository;
import com.team3.springProject.userTable.UserTable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {

   private final ReservationRepository reservationRepository;
   private final UserRepository userRepository;
   private final ClassTableService classTableService;

   @Transactional
   public List<ReservationTable> getReservationByUserId(String userId) {

      Optional<UserTable> userTable = userRepository.findByLoginId(userId);
      List<ReservationTable> reservationTable = reservationRepository.findByUserTable(userTable.get());
      return reservationTable;
   }

   public List<ReservationTable> getReservationByUserIdSortedByCreateAtDesc(String userId) {
      UserTable user = userRepository.findByLoginId(userId).orElseThrow(() -> new RuntimeException("User not found"));
      return reservationRepository.findByUserTableOrderByCreateAtDesc(user);
   }

   public int getTotalReservedCount(ClassTable classTable, LocalDate reservationDay, String time) {
      List<ReservationTable> reservations = reservationRepository.findByClassTableAndReservationDayAndTime(classTable,
            reservationDay, time);

      int totalReservedCount = reservations.stream().mapToInt(ReservationTable::getCount).sum();

      return totalReservedCount;
   }

   // 예약을 생성하는 메소드
   @Transactional
   public void createReservation(String userId, String className, LocalDate date, String time, int count) {
      
      Optional<UserTable> userTable = userRepository.findByLoginId(userId);
      UserTable user = userTable.get();
      ClassTable classTable = classTableService.getClassByName(className);

      // 예약 가능 여부 확인
      int reservedCount = getTotalReservedCount(classTable, date, time);
      System.out.println(reservedCount + "count 검사");
      if (reservedCount + count > classTable.getMaxCapacity()) {
         throw new IllegalStateException("선택한 날짜와 시간에 예약할 수 있는 인원을 초과했습니다.");
      }

      // 새로운 예약 엔티티 생성
      ReservationTable reservation = new ReservationTable();
      reservation.setUserTable(user);
      reservation.setClassTable(classTable);
      reservation.setReservationDay(date);
      reservation.setTime(time);
      reservation.setCount(count);
      reservation.setState("예약완료");
      reservation.setCreateAt(LocalDateTime.now());

      // 예약 정보를 리포지토리에 저장
      try {
         reservationRepository.save(reservation);
      } catch (Exception e) {
         // 예외 발생 시 처리 로직
         // 여기서는 예외 메시지를 로그에 기록하거나 다시 사용자에게 보여줄 수 있는 방법을 구현할 수 있습니다.
         throw new IllegalStateException("예약 저장 중 오류가 발생했습니다. 다시 시도해주세요.");

      }
   }

   // 예약 삭제처리
   @Transactional
   public void deleteReservation(Long id) {
      ReservationTable reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));
      reservationRepository.delete(reservation);
   }

   @Transactional
   public void completeReservation(Long reservationId, String state) throws Exception {
      ReservationTable reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("예약을 찾을 수 없습니다: " + reservationId));

      reservation.setState("결제완료");

      reservationRepository.save(reservation);
   }
   
   //탈퇴 메서드
    @Transactional
    public void deleteReservationByUser(String loginId) {
       Optional<UserTable> userTable2=userRepository.findByLoginId(loginId);
       reservationRepository.deleteByUserTable(userTable2.get());
    }
}
