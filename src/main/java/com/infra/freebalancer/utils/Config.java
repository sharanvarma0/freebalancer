package com.infra.freebalancer.utils;

import java.util.ArrayList;

public class Config {

  private ArrayList<NodeConfig> nodes;
  private String type;

  public ArrayList<NodeConfig> getNodes() {
    return nodes;
  }

  public String algorithm() {
    return type;
  }

}
