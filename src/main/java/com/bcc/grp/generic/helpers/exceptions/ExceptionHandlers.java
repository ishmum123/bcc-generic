package com.bcc.grp.generic.helpers.exceptions;

import com.bcc.grp.generic.config.ServiceConfiguration;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@RestControllerAdvice
public class ExceptionHandlers {

    private final ServiceConfiguration serviceConfiguration;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    public ExceptionHandlers(@Autowired ServiceConfiguration serviceConfiguration) {
        this.serviceConfiguration = serviceConfiguration;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceExceptionHolder.GenericNotFoundException.class)
    public ApiErrorResponse handleUserNotFoundException(final ServiceExceptionHolder.GenericNotFoundException ex) {
        return getProcessedApiErrorResponse(new ApiErrorResponse(ex.getMessage(), ex));
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiErrorResponse handleUnsupportedMediaType(final HttpMediaTypeNotSupportedException ex) {
        return getProcessedApiErrorResponse(new ApiErrorResponse(ex.getLocalizedMessage()));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiErrorResponse handleValidationError(final HttpMessageNotReadableException ex) {
        return getProcessedApiErrorResponse(new ApiErrorResponse("Could not read the request body"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiErrorResponse handleInvalidRequestParam(final MethodArgumentTypeMismatchException ex) {
        return getProcessedApiErrorResponse(new ApiErrorResponse("Invalid Request Parameters"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleValidationError(final MethodArgumentNotValidException ex) {
        Errors validtionErrors = ex.getBindingResult();
        String message = "Validation failed. " + validtionErrors.getErrorCount() + " error(s)";

        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : validtionErrors.getFieldErrors())
            errors.add(Objects.requireNonNull(fieldError).getField() + " " + fieldError.getDefaultMessage());

        for (ObjectError objectError: validtionErrors.getGlobalErrors())
            errors.add(objectError.getDefaultMessage());

        return getProcessedApiErrorResponse(new ApiErrorResponse(message, errors));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiErrorResponse handleNoHandlerFoundError(final NoHandlerFoundException e) {
        return getProcessedApiErrorResponse(new ApiErrorResponse("Invalid URL"));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleThrowable(final Exception ex) {
        return getProcessedApiErrorResponse(new ApiErrorResponse(
                "Internal server error while processing request.",
                ex.getClass().getCanonicalName()
        ));
    }

    private ApiErrorResponse getProcessedApiErrorResponse(ApiErrorResponse apiErrorResponse) {
        LOGGER.error(apiErrorResponse.toString());
        return apiErrorResponse;
    }

    @Getter
    @ToString
    private class ApiErrorResponse {

        private final String code;
        private final String message;
        private final List<String> errors;

        private ApiErrorResponse(String message, List<String> errors, ServiceExceptionHolder.ServiceException e) {
            this.code = serviceConfiguration.getShortCode() + e.getCode();
            this.message = message;
            this.errors = errors;
        }

        private ApiErrorResponse(String message, ServiceExceptionHolder.ServiceException e) {
            this(message, new ArrayList<>(), e);
        }

        private ApiErrorResponse(String message, List<String> errors) {
            this(message, errors, new ServiceExceptionHolder.ServiceException(1000, message));
        }

        private ApiErrorResponse(String message) {
            this(message, Collections.singletonList(message));
        }

        private ApiErrorResponse(String message, String errorMessage) {
            this(message, Collections.singletonList(errorMessage));
        }
    }
}