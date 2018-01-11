package com.levinzhang.springreactive;

import com.levinzhang.springreactive.handler.StudentHandler;
import com.levinzhang.springreactive.repository.StudentRepository;
import com.levinzhang.springreactive.repository.impl.StudentRepositoryImpl;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class Server {

    public static final String HOST = "127.0.0.1";

    public static final int PORT = 8089;

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.startReactorServer();
//		server.startTomcatServer();

        System.out.println("Press ENTER to exit.");
        System.in.read();
    }

    public RouterFunction<ServerResponse> routingFunction() {
        StudentRepository repository = new StudentRepositoryImpl();
        StudentHandler handler = new StudentHandler(repository);

        return nest(path("/student"),
                nest(accept(APPLICATION_JSON),
                        route(GET("/{id}"), handler::getStudentById)
                                .andRoute(method(HttpMethod.GET), handler::getAllStudent)
                ).andRoute(POST("/").and(contentType(APPLICATION_JSON)), handler::createStudent));
    }

    public void startReactorServer() throws InterruptedException {
        RouterFunction<ServerResponse> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);

        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create(HOST, PORT);
        server.newHandler(adapter).block();
    }

    public void startTomcatServer() throws LifecycleException {
        RouterFunction<?> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);

        Tomcat tomcatServer = new Tomcat();
        tomcatServer.setHostname(HOST);
        tomcatServer.setPort(PORT);
        Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
        ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        rootContext.addServletMapping("/", "httpHandlerServlet");
        tomcatServer.start();
    }
}
