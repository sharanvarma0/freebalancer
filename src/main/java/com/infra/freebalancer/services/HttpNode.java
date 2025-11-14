package com.infra.freebalancer.services;

import com.infra.freebalancer.interfaces.Node;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.*;
import com.infra.freebalancer.utils.Utils;

import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class HttpNode implements Node {
  private String ipAddress;
  private Integer port;
  private Logger logger;
  private HttpClient requestClient;

  public HttpNode(HttpClient client, String ipAddress, Integer port) {
    logger = Logger.getGlobal();
    requestClient = client;
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public Integer getPort() {
    return port;
  }

  public HttpResponse<String> serveRequest(HttpServletRequest request) {
    URI pathURL;
    String method = request.getMethod();
    try {
      pathURL = Utils.buildURIfromServletRequest(request);
    } catch (URISyntaxException uriex) {
      logger.finest("Failed to construct URL properly when attempting to request Node: " + uriex);
      return null;
    }
    HttpResponse<String> response = null;
    URI newURL = null;

    try {
      newURL = new URI(pathURL.getScheme(), pathURL.getUserInfo(), ipAddress, port,
          pathURL.getPath(), pathURL.getQuery(), pathURL.getFragment());
    } catch (Exception ex) {
      logger.fine("Problem with creating URL: " + ex);
      return response;
    }

    HttpRequest newRequest = HttpRequest.newBuilder().method(method, BodyPublishers.noBody()).uri(newURL)
        .build();

    try {
      response = requestClient.send(newRequest, BodyHandlers.ofString());
    } catch (Exception ex) {
      logger.fine("Interrupted Exception thrown: " + ex);
    }
    return response;

  }
}
