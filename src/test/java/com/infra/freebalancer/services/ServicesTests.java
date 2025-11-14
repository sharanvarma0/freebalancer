package com.infra.freebalancer.services;

import com.infra.freebalancer.TestDataUtil;
import com.infra.freebalancer.interfaces.LoadBalancer;
import com.infra.freebalancer.interfaces.Node;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@SpringBootTest
public class ServicesTests {

    private final HttpClient testClient = HttpClient.newBuilder().build();
    @Test
    public void testNodeCreation() {
        String randomIP = "127.0.0.1";
        Integer randomPort = 80;

        HttpNode testNode = new HttpNode(testClient, randomIP, randomPort);

        assert(testNode.getIpAddress()).equals("127.0.0.1");
        assert(testNode.getPort()).equals(80);
    }

    @Test
    public void testRRLoadBalancerCreation() {

        String content = "{\"expression\": \"True\"}";
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        LoadBalancer testlb = TestDataUtil.createTestRoundRobinLoadBalancer();
        ArrayList<Node> nodes = testlb.getNodes();
        assert(nodes.size() == 3);
    }

    @Test
    public void testLoadBalancerServe() {

        String content = "{\"expression\": \"True\"}";
        LoadBalancer testlb = Mockito.mock(RoundRobinLoadBalancer.class);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockResponse.body()).thenReturn("""
                {"success": true, "trigger": "_success_", "parameters": {"result": true, "tags": {}}, "debug": {"requests_log": []}"}
        """);

        Mockito.when(mockRequest.getScheme()).thenReturn("http");
        Mockito.when(mockRequest.getRequestURI()).thenReturn("generic_eval");
        Mockito.when(mockRequest.getServerName()).thenReturn("randomServer");
        Mockito.when(mockRequest.getServerPort()).thenReturn(443);
        Mockito.when(mockRequest.getQueryString()).thenReturn("");
        Mockito.when(mockRequest.getContentType()).thenReturn("application/json");
        Mockito.when(mockRequest.getMethod()).thenReturn("POST");
        System.out.println(mockRequest.getRequestURI());
        Mockito.when(testlb.serve(mockRequest)).thenReturn(mockResponse);
        HttpResponse<String> testResponse = testlb.serve(mockRequest);
        String json = testResponse.body();
        System.out.println(json);
        assert(mockResponse.statusCode() == 200);
    }
}
