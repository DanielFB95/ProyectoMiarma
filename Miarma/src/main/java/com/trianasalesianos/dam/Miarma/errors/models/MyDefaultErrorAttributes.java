package com.trianasalesianos.dam.Miarma.errors.models;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class MyDefaultErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        Map<String,Object> errorAttributes = super.getErrorAttributes(webRequest,options);
        Map<String,Object> result = Map.of(
                "status",errorAttributes.get("status"),
                "code", HttpStatus.valueOf((int) errorAttributes.get("status")).name(),
                "date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                "route",errorAttributes.get("path")
        );

        if(errorAttributes.containsKey("message")){
            result.put("message", errorAttributes.get("message"));
        }
        if(errorAttributes.containsKey("errors")){
            result.put("subErrors",errorAttributes.get("errors"));
        }
        return result;
    }
}