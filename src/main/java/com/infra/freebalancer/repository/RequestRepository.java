package com.infra.freebalancer.repository;

import com.infra.freebalancer.models.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface RequestRepository extends CrudRepository<Request, Long> {
}
