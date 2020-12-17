package io.ayers.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

//@SoapFault(faultCode = FaultCode.CLIENT)
@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://ayers.io/courses}001_COURSE_NOT_FOUND")
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
