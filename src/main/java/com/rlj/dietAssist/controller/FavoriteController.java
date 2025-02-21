package com.rlj.dietAssist.controller;

import com.rlj.dietAssist.dto.FavoriteDto;
import com.rlj.dietAssist.jwt.CustomUserDetails;
import com.rlj.dietAssist.service.FavoriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

  private final FavoriteService favoriteService;

  @GetMapping("/{userId}")
  public ResponseEntity<List<FavoriteDto>> getFavorites(@AuthenticationPrincipal CustomUserDetails user) {
    List<FavoriteDto> favorites = favoriteService.getFavorites(user.getId());
    return ResponseEntity.ok(favorites);
  }


  @PostMapping("/add")
  public ResponseEntity<?> addFavorite(@AuthenticationPrincipal CustomUserDetails user,
      @RequestParam Long foodId, @RequestParam String notes) {

    FavoriteDto favoriteDto =  favoriteService.addFavorite(user.getId(), foodId, notes);

    return ResponseEntity.ok(favoriteDto);
  }

  @DeleteMapping("/{foodId}")
  public ResponseEntity<Void> removeFavorite(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long foodId) {
    favoriteService.deleteFavorite(user.getId(), foodId);
    return ResponseEntity.noContent().build();
  }



}
