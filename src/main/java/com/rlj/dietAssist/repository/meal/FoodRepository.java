package com.rlj.dietAssist.repository.meal;

import com.rlj.dietAssist.entity.diet.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

  Boolean existsByName(String name);



}
