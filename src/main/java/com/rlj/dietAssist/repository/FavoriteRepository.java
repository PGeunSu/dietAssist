package com.rlj.dietAssist.repository;

import com.rlj.dietAssist.entity.favorite.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  List<Favorite> findByUserId(Long userId);

  Optional<Favorite> findByUserIdAndFoodId(Long userId, Long foodId);

  Boolean existsByFoodId(Long foodId);

}
