package com.infra.freebalancer.utils;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Config {

  private ArrayList<NodeConfig> nodes;
  private String type;

  public ArrayList<NodeConfig> getNodes() {
    return nodes;
  }

  public String toString() {
      String nodeString = "";
      for (NodeConfig config: nodes) {
          nodeString += config.toString();
      }
      return String.format("type: %s, Nodes: %s", type, nodeString);
  }

}
