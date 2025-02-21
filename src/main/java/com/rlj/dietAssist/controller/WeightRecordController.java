package com.rlj.dietAssist.controller;

import com.rlj.dietAssist.dto.WeightRecordDto;
import com.rlj.dietAssist.jwt.CustomUserDetails;
import com.rlj.dietAssist.service.WeightRecordService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weightRecord")
public class WeightRecordController {

  private final WeightRecordService weightRecordService;

  @PostMapping("/add")
  public ResponseEntity<?> addWeightRecord(@AuthenticationPrincipal CustomUserDetails user, @RequestParam float weight, @RequestParam LocalDate date){

    WeightRecordDto weightRecordDto = weightRecordService.saveWeightRecord(user.getId(), weight, date);

    return ResponseEntity.ok(weightRecordDto);
  }

  @GetMapping("/{recordId}")
  public ResponseEntity<?> showRecord(@PathVariable Long recordId){

    WeightRecordDto weightRecordDto = weightRecordService.getWeightRecord(recordId);

    return ResponseEntity.ok(weightRecordDto);
  }

  @GetMapping("/records")
  public ResponseEntity<?> showRecords(@AuthenticationPrincipal CustomUserDetails user,
      @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){

    List<WeightRecordDto> records = weightRecordService.getDateWeightRecord(user.getId(), startDate, endDate);

    return ResponseEntity.ok(records);
  }

  @PatchMapping("/{recordId}/update")
  public ResponseEntity<?> updateWeightRecord(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long recordId ,@RequestParam float weight){

    WeightRecordDto weightRecordDto = weightRecordService.updateRecord(user.getId(), recordId, weight);

    return ResponseEntity.ok(weightRecordDto);
  }




}
