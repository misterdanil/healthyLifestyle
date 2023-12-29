package org.healthylifestyle.user.repository;

import java.util.List;

import org.healthylifestyle.user.model.lifestyle.healthy.Parameter;
import org.healthylifestyle.user.model.lifestyle.healthy.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ParameterRepository extends CrudRepository<Parameter, Long> {
	@Query("select p from Parameter p inner join p.healthy h inner join h.user u where u.id = :userId and p.status = :status")
	List<Parameter> findAllByUserAndStatus(Long userId, Status status);

	@Query("select p from Parameter p where p.parameterType.id = :parameterTypeId and p.status = :status")
	List<Parameter> findByParameterTypeAndStatus(Long parameterTypeId, Status status);

	@Query("select p from Parameter p where p.parameterType.id = :parameterTypeId and p.healthy.user.id = :userId and p.status = :status")
	List<Parameter> findByUserAndParameterTypeAndStatus(Long userId, Long parameterTypeId, Status status);

	@Query("select p from Parameter p where p.parameterType.id = :parameterTypeId and p.healthy.user.id = :userId and p.status = 'ACTUAL'")
	Parameter findActualByUserAndParameterType(Long userId, Long parameterTypeId);

}
