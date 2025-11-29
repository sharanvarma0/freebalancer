package com.infra.freebalancer.services;

import com.infra.freebalancer.models.Request;
import com.infra.freebalancer.models.Response;
import com.infra.freebalancer.repository.RequestRepository;
import com.infra.freebalancer.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.infra.freebalancer.interfaces.LoadBalancer;
import com.infra.freebalancer.interfaces.Node;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.net.http.*;
import java.util.logging.*;
import jakarta.servlet.http.HttpServletRequest;


public class RoundRobinLoadBalancer implements LoadBalancer {
  private ArrayList<Node> listOfNodes = null;
  private static Integer currentNode = 0;
  private Logger logger = Logger.getLogger("com.infra.freebalancer.services");

  @Autowired
  private RequestRepository requestRepo;

  @Autowired
  private ResponseRepository responseRepo;

  public RoundRobinLoadBalancer(ArrayList<Node> httpNodes) {
    listOfNodes = httpNodes;
    logger.fine("Initialized RoundRobinLoadBalancer with Nodes: " + listOfNodes);
  }

  public ArrayList<Node> getNodes() {
      return listOfNodes;
  }

  private Request saveRequest(HttpServletRequest request) {
      String method = request.getMethod();
      String sourceIp = request.getRemoteAddr();
      String path = request.getRequestURI();
      Request requestObj = new Request(method, sourceIp, path);
      System.out.println("Created new request");
      requestRepo.save(requestObj);
      return requestObj;
  }

  private void saveResponse(Node servedNode, Request request, Duration responseTime) {
      String ipAddress = servedNode.getIpAddress();
      Response responseObj = new Response(request, ipAddress, responseTime.toMillis());
      System.out.println("Created new Response");
      responseRepo.save(responseObj);
  }

  public HttpResponse<String> serve(HttpServletRequest request) {
    Node node = listOfNodes.get(currentNode);
    logger.fine("Request being served by: " + node.getIpAddress() + ":" + node.getPort());
    Instant start = Instant.now();
    HttpResponse<String> response = node.serveRequest(request);
    Instant end = Instant.now();
    Duration responseTime = Duration.between(start, end);
    Request newRequest = saveRequest(request);
    saveResponse(node, newRequest, responseTime);
    logger.fine("Incrementing Node index for next request");
    currentNode = (currentNode + 1) % listOfNodes.size();
    logger.fine("Incremented");

    return response;
  }
}
