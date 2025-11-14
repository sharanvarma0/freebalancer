package com.infra.freebalancer.interfaces;

import java.net.http.HttpResponse;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;

public interface LoadBalancer {
    ArrayList<Node> getNodes();
  HttpResponse<String> serve(HttpServletRequest request);
}
