package com.infra.freebalancer.interfaces;

import java.net.http.HttpResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface Node {
  HttpResponse<String> serveRequest(HttpServletRequest request);

  String getIpAddress();

  Integer getPort();
}
