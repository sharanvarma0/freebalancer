package com.infra.freebalancer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nodes")
public class Node {

  @Id
  private Long id;
  private String ipAddress;
  private Integer port;
  private String healthCheckPath;
}
