package com.infra.freebalancer.utils;

import java.util.logging.Logger;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import com.infra.freebalancer.interfaces.*;
import com.infra.freebalancer.services.*;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.http.*;

public class Utils {
  public static Logger logger = Logger.getLogger("Utils");

  public static HttpClient createNewHttpClient() {
    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    return client;
  }

  public static ArrayList<Node> initializeNodes(ArrayList<NodeConfig> nodes) {

    ArrayList<Node> initializedNodes = new ArrayList<Node>();

    for (NodeConfig config : nodes) {
      String ipAddress = config.address;
      Integer port = config.port;

      HttpClient newClient = createNewHttpClient();

      Node node = new HttpNode(newClient, ipAddress, port);

      if (node != null) {
        logger.fine("Initialized Node: " + ipAddress + ":" + port);
        initializedNodes.add(node);
      }
    }
    logger.fine("Total Nodes Initialized: " + initializedNodes.size());

    return initializedNodes;
  }

  public static URI buildURIfromServletRequest(HttpServletRequest request) throws URISyntaxException {
    String scheme = request.getScheme();
    String host = request.getServerName();
    Integer port = request.getServerPort();
    String queryParams = request.getQueryString();
    String path = request.getRequestURI();

    StringBuilder url = new StringBuilder();
    url.append(scheme).append("://").append(host).append(":").append(port).append(path);

    if (queryParams != null) {
      url.append("?").append(queryParams);
    }

    return new URI(url.toString());
  }
}
