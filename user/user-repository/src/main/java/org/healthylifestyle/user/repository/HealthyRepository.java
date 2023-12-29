package org.healthylifestyle.user.repository;

import org.healthylifestyle.user.model.lifestyle.healthy.Healthy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthyRepository extends CrudRepository<Healthy, Long> {

}
