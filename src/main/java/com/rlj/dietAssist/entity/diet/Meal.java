package com.rlj.dietAssist.entity.diet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rlj.dietAssist.entity.base.BaseEntity;
import com.rlj.dietAssist.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Meal extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE) // 회원의 계정이 삭제되었을 경우 같이 삭제
  private User user;

  @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<MealFood> mealFoods = new ArrayList<>();

  private String name;

  private float totalEnergy;
  private float totalCarbohydrate;
  private float totalProtein;
  private float totalFat;
  private float totalSugar;

  public void addMealFood(MealFood mealFood) {
    mealFoods.add(mealFood);
    mealFood.setMeal(this);
    updateNutrients();
  }

  public void removeMealFood(MealFood mealFood) {
    mealFoods.remove(mealFood);
    mealFood.setMeal(null);
    updateNutrients();
  }

  public void setMealUser(String name, User user){
    this.name = name;
    this.user = user;
  }

  public void updateNutrients() {
    this.totalEnergy = (float) mealFoods.stream().mapToDouble(MealFood::getEnergy).sum();
    this.totalCarbohydrate = (float) mealFoods.stream().mapToDouble(MealFood::getCarbohydrate).sum();
    this.totalProtein = (float) mealFoods.stream().mapToDouble(MealFood::getProtein).sum();
    this.totalFat = (float) mealFoods.stream().mapToDouble(MealFood::getFat).sum();
    this.totalSugar = (float) mealFoods.stream().mapToDouble(MealFood::getSugar).sum();
  }

}
