package com.infra.freebalancer.utils;

import lombok.Data;

@Data
public class NodeConfig {
  public String address;
  public Integer port;

  public String toString() {
      return String.format("%s:%d ", address, port);
  }
}
