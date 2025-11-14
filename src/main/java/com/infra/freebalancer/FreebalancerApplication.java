package com.infra.freebalancer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infra.freebalancer.interfaces.*;
import com.infra.freebalancer.services.RoundRobinLoadBalancer;
import com.infra.freebalancer.utils.*;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletRequestWrapper;
import java.net.http.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@SpringBootApplication
@RestController
public class FreebalancerApplication {

  public static void main(String[] args) {
    SpringApplication.run(FreebalancerApplication.class, args);
  }

  @RequestMapping("/")
  public ResponseEntity<HttpResponse<String>> run(HttpServletRequest request) {
    Config loadBalancerConfig = ConfigLoader.getConfig();
    ArrayList<NodeConfig> nodes = loadBalancerConfig.getNodes();
    ArrayList<Node> initializedNodes = Utils.initializeNodes(nodes);

    LoadBalancer loadBalancer = new RoundRobinLoadBalancer(initializedNodes);

    HttpResponse<String> loadBalancerResponse = loadBalancer.serve(request);
    return ResponseEntity.ok(loadBalancerResponse);

  }

}
