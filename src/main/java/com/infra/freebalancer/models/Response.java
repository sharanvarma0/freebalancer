package com.infra.freebalancer.models;

import java.io.Serializable;
import com.infra.freebalancer.interfaces.Node;

import jakarta.persistence.*;
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private Request requestId;
  private String servedBy;
  private Long responseTime;

  public Response(Request requestId, String servedBy, Long responseTime) {
      this.requestId = requestId;
      this.servedBy = servedBy;
      this.responseTime = responseTime;
  }
}
