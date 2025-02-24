package com.rlj.dietAssist.repository.meal;

import com.rlj.dietAssist.entity.diet.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {

}
