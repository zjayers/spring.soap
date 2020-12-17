package io.ayers.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Collections;
import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    // Expose Web Services Endpoints
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {

        var messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(messageDispatcherServlet, "/ws/*");
    }

    // Generate and Expose the WSDL
    @Bean(name = "courses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {

        var definition = new DefaultWsdl11Definition();

        definition.setPortTypeName("CoursePort");
        definition.setTargetNamespace("http://ayers.io/courses");
        definition.setLocationUri("/ws");
        definition.setSchema(coursesSchema);

        return definition;
    }

    // Define Schema
    @Bean
    public XsdSchema coursesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
    }

    // Configure Security
    @Bean
    public XwsSecurityInterceptor securityInterceptor() {
        var securityInterceptor = new XwsSecurityInterceptor();

        securityInterceptor.setCallbackHandler(callbackHandler());
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));

        return securityInterceptor;
    }

    // Setup Password and User callback handler
    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();

        handler.setUsersMap(Collections.singletonMap("user", "password"));

        return handler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }
}
