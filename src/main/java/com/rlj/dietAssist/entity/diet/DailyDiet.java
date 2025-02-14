package com.rlj.dietAssist.entity.diet;


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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class DailyDiet extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE) // 회원의 계정이 삭제되었을 경우 같이 삭제
  private User user;

  @OneToMany(mappedBy = "dailyDiet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<DailyMeal> meals = new ArrayList<>();

  private LocalDate date;

  private float totalEnergy;
  private float totalCarbohydrate;
  private float totalProtein;
  private float totalFat;
  private float totalSugar;

  public void addMeal(DailyMeal dailyMeal) {
    meals.add(dailyMeal);
    dailyMeal.setDailyDiet(this);
    updateNutrients();
  }

  public void removeMeal(DailyMeal dailyMeal) {
    meals.remove(dailyMeal);
    dailyMeal.setDailyDiet(null);
    updateNutrients();
  }

  // 영양소 값 업데이트 메서드
  public void updateNutrients() {
    this.totalEnergy = (float) meals.stream().mapToDouble(DailyMeal::getTotalEnergy).sum();
    this.totalCarbohydrate = (float) meals.stream().mapToDouble(DailyMeal::getTotalCarbohydrate).sum();
    this.totalProtein = (float) meals.stream().mapToDouble(DailyMeal::getTotalProtein).sum();
    this.totalFat = (float) meals.stream().mapToDouble(DailyMeal::getTotalFat).sum();
    this.totalSugar = (float) meals.stream().mapToDouble(DailyMeal::getTotalSugar).sum();
  }


}
