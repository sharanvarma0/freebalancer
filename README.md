# FreeBalancer: A load balancer written in JAVA

## Features
- Track and Configure nodes
- Manage Node connections
- Track Request metrics
- Track Response Metrics
- User Defined Load Balancing Algorithms

## Database Tables

1. Nodes: Contains Nodes to which loads will be balanced
  - Suggested to use private IP Addresses of nodes
2. Config: Contains FreeBalancer Configuration
3. Requests: Contains Requests Metrics. id, method, time of request, path, etc
4. Responses: Contains Response Metrics. id, request_id, time of response, node served, time to serve, etc.
