package com.rlj.dietAssist.repository.meal;

import com.rlj.dietAssist.entity.diet.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


}
