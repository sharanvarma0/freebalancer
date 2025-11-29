package com.infra.freebalancer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String method;
  private String sourceIpAddress;
  private String path;

    public Request(String method, String sourceIp, String path) {
        this.method = method;
        this.sourceIpAddress = sourceIp;
        this.path = path;
    }
}
