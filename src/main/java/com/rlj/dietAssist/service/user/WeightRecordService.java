package com.rlj.dietAssist.service.user;

import static com.rlj.dietAssist.exception.ErrorCode.ALREADY_EXISTING_RECORD;
import static com.rlj.dietAssist.exception.ErrorCode.ERROR_DATE;

import com.rlj.dietAssist.dto.user.WeightRecordDto;
import com.rlj.dietAssist.entity.user.User;
import com.rlj.dietAssist.entity.user.WeightRecord;
import com.rlj.dietAssist.exception.BaseException;
import com.rlj.dietAssist.exception.Exception;
import com.rlj.dietAssist.repository.user.WeightRecordRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeightRecordService {

  private final WeightRecordRepository weightRecordRepository;
  private final BaseException baseException;


  //기록 입력
  @Transactional
  public WeightRecordDto saveWeightRecord(Long userId, float weight, LocalDate date){
    User user = baseException.getUser(userId);

    //오늘 이후의 날짜는 입력 금지
    if (date.isAfter(LocalDate.now())) {
      throw new Exception(ERROR_DATE);
    }

    //데이터 중복 방지
    if (weightRecordRepository.existsByDate(date)){
      throw new Exception(ALREADY_EXISTING_RECORD);
    }

    WeightRecord weightRecord = WeightRecord.builder()
        .user(user)
        .weight(weight)
        .date(date)
        .build();

    weightRecordRepository.save(weightRecord);

    //유저 체중 업데이트(전 날 입력이 아닌 당일 입력인 경우만)
    if (user.getWeight() != weight && date.isEqual(LocalDate.now())){
      user.updateWeight(weight);
    }

    return WeightRecordDto.from(weightRecord);
  }

  //날짜 하나만 조회
  public WeightRecordDto getWeightRecord(Long weightRecordId){

    WeightRecord weightRecord = baseException.getWeightRecord(weightRecordId);

    return WeightRecordDto.from(weightRecord);
  }

  //기록 시간 조회
  public List<WeightRecordDto> getDateWeightRecord(Long userId, LocalDate startDate, LocalDate endDate){
    baseException.validateUserExists(userId);

    List<WeightRecord> records = weightRecordRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate);

    return records.stream()
        .map(WeightRecordDto::from)
        .collect(Collectors.toList());
  }

  //기록 수정
  @Transactional
  public WeightRecordDto updateRecord(Long userId, Long weightRecordId, float weight){
    baseException.validateUserExists(userId);

    WeightRecord weightRecord = baseException.getWeightRecord(weightRecordId);

    weightRecord.modifiedRecord(weight);

    return WeightRecordDto.from(weightRecord);
  }





}
