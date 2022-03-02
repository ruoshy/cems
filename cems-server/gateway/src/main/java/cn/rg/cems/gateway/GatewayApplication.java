package cn.rg.cems.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 网关路由 分发请求到对应服务
     *
     * @param builder
     * @return
     * @author cwc
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("manage-route", r -> r.path("/m/**").filters(f -> f.stripPrefix(1))
                        .uri("lb://manage-service"))
                .route("enter-route", r -> r.path("/e/**").filters(f -> f.stripPrefix(1))
                        .uri("lb://enter-service"))
                .route("login-route", r -> r.path("/l/**").filters(f -> f.stripPrefix(1))
                        .uri("lb://login-service"))
                .build();
    }

}
