package me.anichakra.poc.pilot.driver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.anichakra.poc.pilot.driver.domain.Driver;

@Repository
public interface DriverRepository extends JpaRepository <Driver, Long> {
  public List<Driver> findByCategory_segmentAndCategory_type(String segment, String type);
}
