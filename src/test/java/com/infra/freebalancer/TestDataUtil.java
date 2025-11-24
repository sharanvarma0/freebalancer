package com.infra.freebalancer;

import com.infra.freebalancer.interfaces.LoadBalancer;
import com.infra.freebalancer.interfaces.Node;
import com.infra.freebalancer.services.HttpNode;
import com.infra.freebalancer.services.RoundRobinLoadBalancer;

import java.net.http.HttpClient;
import java.util.ArrayList;

public class TestDataUtil {

    public static Node createTestHttpNode(String IPAddress, Integer port) {
        HttpClient testClient = createTestClient();

        return new HttpNode(testClient, IPAddress, port);
    }

    public static HttpClient createTestClient() {
        return HttpClient.newBuilder().build();
    }

    public static Node createTestHttpNodeA() {
        return createTestHttpNode("127.0.0.1", 11000);
    }

    public static Node createTestHttpNodeB() {
        return createTestHttpNode("127.0.0.1", 11000);
    }

    public static Node createTestHttpNodeC() {
        return createTestHttpNode("127.0.0.1", 11000);
    }

    public static LoadBalancer createTestRoundRobinLoadBalancer() {
        ArrayList<Node> testNodes = new ArrayList<>();
        testNodes.add(createTestHttpNodeA());
        testNodes.add(createTestHttpNodeB());
        testNodes.add(createTestHttpNodeC());

        return new RoundRobinLoadBalancer(testNodes);
    }
}
