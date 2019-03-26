package me.anichakra.poc.pilot.driver.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import me.anichakra.poc.pilot.driver.domain.Category;
import me.anichakra.poc.pilot.framework.annotation.DataAccess;

@DataAccess
public interface CategoryRepository extends JpaRepository <Category, Long> {

}
