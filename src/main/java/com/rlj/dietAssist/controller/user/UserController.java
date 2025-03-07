package com.rlj.dietAssist.controller.user;

import com.rlj.dietAssist.dto.user.ChangedPassword;
import com.rlj.dietAssist.dto.user.UserDto;
import com.rlj.dietAssist.dto.user.UserModifiedDto;
import com.rlj.dietAssist.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{userId}/detail")
  public ResponseEntity<UserDto> detail(@PathVariable Long userId){

    UserDto user = userService.detail(userId);

    return ResponseEntity.ok(user);
  }



  @PutMapping("/{userId}/update")
  public ResponseEntity<UserDto> update(@PathVariable Long userId,
                                  @Valid @RequestBody UserModifiedDto dto) {
    UserDto userDto = userService.update(userId, dto);

    return ResponseEntity.ok(userDto);
  }

  @PatchMapping("/{userId}/password")
  public ResponseEntity<ChangedPassword> password(@PathVariable Long userId, @Valid @RequestBody ChangedPassword dto) {

    ChangedPassword changedPassword = userService.changePassword(userId, dto);

    return ResponseEntity.ok(changedPassword);
  }

  @DeleteMapping("/{userId}/delete")
  public ResponseEntity<Void> update(@PathVariable Long userId, String password) {

    userService.delete(userId, password);

    return ResponseEntity.noContent().build();
  }










}
