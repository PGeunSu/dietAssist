package com.rlj.dietAssist.entity.diet;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rlj.dietAssist.dto.FoodMacroDto;
import com.rlj.dietAssist.entity.base.BaseEntity;
import com.rlj.dietAssist.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Meal, Food 테이블의 N:M 관계를 위한 중간 테이블
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class MealFood extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "meal_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Meal meal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Food food;

  private float weight;

  private float energy;
  private float carbohydrate;
  private float protein;
  private float fat;
  private float sugar;

  public MealFood(Meal meal, User user, Food food, FoodMacroDto macroDto) {
    this.meal = meal;
    this.user = user;
    this.food = food;
    this.weight = macroDto.getWeight();
    this.energy = macroDto.getEnergy();
    this.carbohydrate = macroDto.getCarbohydrate();
    this.protein = macroDto.getProtein();
    this.fat = macroDto.getFat();
    this.sugar = macroDto.getSugar();
  }

  public void update(Food food, FoodMacroDto macroDto) {
    this.food = food;
    this.weight = macroDto.getWeight();
    this.energy = macroDto.getEnergy();
    this.carbohydrate = macroDto.getCarbohydrate();
    this.protein = macroDto.getProtein();
    this.fat = macroDto.getFat();
    this.sugar = macroDto.getSugar();
  }
}
