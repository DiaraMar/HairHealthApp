package com.kamikarow.hairCareProject.utility.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {



    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(BadRequestException e){
        HttpStatus request = HttpStatus.BAD_REQUEST; //400
        ApiException apiException= new ApiException(e.getMessage(), request, e, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, request);
    }
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleApiRequestException(UnauthorizedException e){
        HttpStatus request = HttpStatus.UNAUTHORIZED; //401
        ApiException apiException= new ApiException(e.getMessage(), request, e, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, request);
    }
    @ExceptionHandler(value = {AccessRightsException.class})
    public ResponseEntity<Object> handleApiRequestException(AccessRightsException e){
        HttpStatus request = HttpStatus.FORBIDDEN; //403
        ApiException apiException= new ApiException(e.getMessage(), request, e, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, request);
    }
    @ExceptionHandler(value = {RessourceNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(RessourceNotFoundException e){
        HttpStatus request = HttpStatus.NOT_FOUND; //404
        ApiException apiException= new ApiException(e.getMessage(), request, e, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, request);
    }

    @ExceptionHandler(value = {NotAllowedException.class})
    public ResponseEntity<Object> handleApiRequestException(NotAllowedException e){
        HttpStatus request = HttpStatus.NOT_FOUND; //405
        ApiException apiException= new ApiException(e.getMessage(), request, e, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, request);
    }
    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handleApiRequestException(ConflictException e){
        HttpStatus request = HttpStatus.CONFLICT; //409
        ApiException apiException= new ApiException(e.getMessage(), request, e, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, request);
    }

    @ExceptionHandler(value = {InternalServerError.class})
    public ResponseEntity<Object> handleApiRequestException(InternalServerError e){
        HttpStatus request = HttpStatus.INTERNAL_SERVER_ERROR; //409
        ApiException apiException= new ApiException(e.getMessage(), request, e, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, request);
    }

}
