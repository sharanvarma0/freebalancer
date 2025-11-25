package com.infra.freebalancer.models;

import java.io.Serializable;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "responses")
public class Response implements Serializable {

  @Id
  private Long id;
  private Request requestId;
  private Node servedBy;
  private Time responseTime;
}
