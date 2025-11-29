package com.infra.freebalancer.repository;

import com.infra.freebalancer.models.Response;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ResponseRepository extends CrudRepository<Response, Long> {
}
