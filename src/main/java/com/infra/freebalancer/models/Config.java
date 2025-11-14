package com.infra.freebalancer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "config")
public class Config {
  private String algorithm;
  private Integer healthCheckWaitPeriod;
}
