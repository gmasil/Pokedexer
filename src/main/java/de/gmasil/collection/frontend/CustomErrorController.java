package de.gmasil.collection.frontend;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import de.gmasil.collection.frontend.advisor.Template;

@Controller
public class CustomErrorController implements ErrorController {
    private static final String PAGE_NOT_FOUND_MESSAGE = "Page not found";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String error(MethodArgumentTypeMismatchException ex, HttpServletRequest request, Template template) {
        Object forwardRequestUri = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        Integer code;
        String message;
        Exception exception;
        if (forwardRequestUri != null) {
            code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
            exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            if (code == null) {
                code = HttpStatus.INTERNAL_SERVER_ERROR.value();
                message = INTERNAL_SERVER_ERROR_MESSAGE;
                exception = null;
            } else if (code == HttpStatus.NOT_FOUND.value()) {
                message = PAGE_NOT_FOUND_MESSAGE;
                exception = null;
            } else if (code == HttpStatus.FORBIDDEN.value()) {
                exception = null;
            }
        } else {
            code = 404;
            message = PAGE_NOT_FOUND_MESSAGE;
            exception = null;
        }
        return template.makeError(code, message, exception);
    }
}
