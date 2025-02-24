package com.rlj.dietAssist.repository.daily;

import com.rlj.dietAssist.entity.diet.DailyDiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyDietRepository extends JpaRepository<DailyDiet, Long> {

}
