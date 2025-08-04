package dsignpattern.structural;

import java.util.HashMap;
import java.util.Map;

public class ProxyDesginPattern {
}


// I can take an example of api gateway as a proxy

// client, api gateway and microservice
// since client cannot interact with service directly, so it has to go through api gateway
// where request are authorised, security validation, header modification ...etc can be done
// then it will go to correct microservice


interface service{
    void handleRequest(String request);
}

//: Real Subject (Actual Services)

class UserService implements service{

    @Override
    public void handleRequest (String request) {
        System.out.println("UserService handling request ...");
    }
}
//: Real Subject (Actual Services)
class OrderService implements service{

    @Override
    public void handleRequest(String request) {
        System.out.println("OrderService handling request ...");
    }
}


//proxy

class ApiGateway implements service{

    private Map<String, service> services = new HashMap<>();

    // Register available services
    public ApiGateway() {
        services.put("/user", new UserService());
        services.put("/order", new OrderService());
    }

    // API Gateway logic (security, logging, rate limiting, etc.)
    public void handleRequest(String request) {
        if (!isValidRequest(request)) {
           System.out.println("❌ Invalid Request: Access Denied");
            return;
        }

        service service = services.get(request);
        if (service != null) {
            System.out.println("✅ Valid Request: Forwarding to respective service.");
            service.handleRequest(request);
        } else {
            System.out.println("❌ Service Not Found for request: " + request);
        }
    }

    // Example Security/Validation Logic
    private boolean isValidRequest(String request) {
        // Mock security logic — for example, only allow "/user" and "/order"
        return request.equals("/user") || request.equals("/order");
    }
}

// client

/**
 * ✅ Decouples clients from microservices
 * ✅ Centralized entry point for security, logging, and monitoring
 * ✅ Enhances scalability by improving request distribution
 * ✅ Easier to add new services without changing client logic
 */

 class ProxyPatternDemo {
    public static void main(String[] args) {
        service apiGateway = new ApiGateway();

        apiGateway.handleRequest("/user");    // ✅ Valid - Forwarded to UserService
        apiGateway.handleRequest("/order");   // ✅ Valid - Forwarded to OrderService
        apiGateway.handleRequest("/admin");   // ❌ Invalid Service
        apiGateway.handleRequest("/hack");    // ❌ Security Violation
    }
}