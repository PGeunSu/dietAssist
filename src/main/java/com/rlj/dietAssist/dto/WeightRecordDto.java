package com.rlj.dietAssist.dto;

import com.rlj.dietAssist.entity.user.WeightRecord;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeightRecordDto {

  private String userName;
  private float weight;
  private LocalDate date;

  public static WeightRecordDto from(WeightRecord weightRecord){
    return new WeightRecordDto(
        weightRecord.getUser().getName(),
        weightRecord.getWeight(),
        weightRecord.getDate()
    );
  }

}
