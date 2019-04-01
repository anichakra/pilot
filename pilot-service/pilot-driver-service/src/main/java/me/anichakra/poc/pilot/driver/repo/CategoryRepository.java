package me.anichakra.poc.pilot.driver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.anichakra.poc.pilot.driver.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {

}
