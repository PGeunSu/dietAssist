package com.rlj.dietAssist.repository;

import com.rlj.dietAssist.entity.diet.DailyMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMealRepository extends JpaRepository<DailyMeal, Long> {

}
