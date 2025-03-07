package com.rlj.dietAssist.entity.diet;


import com.rlj.dietAssist.dto.food.FoodDto;
import com.rlj.dietAssist.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(value = AuditingEntityListener.class)
public class Food extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private float weight;
  private float energy;
  private float carbohydrate;
  private float sugar;
  private float protein;
  private float fat;

  public static Food from(FoodDto foodDto){
    return Food.builder()
        .name(foodDto.getFoodName())
        .weight(foodDto.getWeight())
        .energy(foodDto.getEnergy())
        .carbohydrate(foodDto.getCarbohydrate())
        .sugar(foodDto.getSugar())
        .protein(foodDto.getProtein())
        .fat(foodDto.getFat())
        .build();
  }


}
