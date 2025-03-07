package com.rlj.dietAssist.entity.diet;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

// DailyDiet, Meal 테이블의 N:M 관계를 위한 중간 테이블
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class DailyMeal extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "daily_diet_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private DailyDiet dailyDiet;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "meal_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Meal meal;

  private float totalEnergy;
  private float totalCarbohydrate;
  private float totalProtein;
  private float totalFat;
  private float totalSugar;

  public DailyMeal(DailyDiet dailyDiet,  User user, Meal meal) {
    this.dailyDiet = dailyDiet;
    this.user = user;
    this.meal = meal;
    this.totalEnergy = meal.getTotalEnergy();
    this.totalCarbohydrate = meal.getTotalCarbohydrate();
    this.totalProtein = meal.getTotalProtein();
    this.totalFat = meal.getTotalFat();
    this.totalSugar = meal.getTotalSugar();
  }
}
