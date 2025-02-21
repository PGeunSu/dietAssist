package com.rlj.dietAssist.repository;

import com.rlj.dietAssist.entity.user.WeightRecord;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {

  Boolean existsByDate(LocalDate date);

  List<WeightRecord> findByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDate startDate, LocalDate endDate);

  }

