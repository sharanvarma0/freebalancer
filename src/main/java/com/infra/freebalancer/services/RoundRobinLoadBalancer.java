package com.infra.freebalancer.services;

import org.springframework.stereotype.Component;

import com.infra.freebalancer.interfaces.LoadBalancer;
import com.infra.freebalancer.interfaces.Node;

import java.util.ArrayList;
import java.net.http.*;
import java.util.logging.*;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RoundRobinLoadBalancer implements LoadBalancer {
  private ArrayList<Node> listOfNodes = null;
  private Integer currentNode = 0;
  private Logger logger = Logger.getLogger("com.infra.freebalancer.services");

  public RoundRobinLoadBalancer(ArrayList<Node> httpNodes) {
    listOfNodes = httpNodes;
    logger.fine("Initialized RoundRobinLoadBalancer with Nodes: " + listOfNodes);
  }

  public ArrayList<Node> getNodes() {
      return listOfNodes;
  }

  public HttpResponse<String> serve(HttpServletRequest request) {
    Node node = listOfNodes.get(currentNode);
    logger.fine("Request being served by: " + node.getIpAddress() + ":" + node.getPort());
    HttpResponse<String> response = node.serveRequest(request);
    logger.fine("Incrementing Node index for next request");
    currentNode = (currentNode + 1) % listOfNodes.size();
    logger.fine("Incremented");

    return response;
  }
}
