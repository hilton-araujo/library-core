package com.biblioteca.gestao_biblioteca.infrastructure.exceptions;

import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class Handler {
    private final Logger logger = LoggerFactory.getLogger(Handler.class);
    private final Map<String, String> errors = new HashMap<>();

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseApi handleValidationExceptions(HttpServletRequest request,
                                                  MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseApi( "Os dados fornecidos não puderam ser processados. Verifique o formato dos dados", errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseApi handleInvalidFormat(HttpServletRequest request,
                                           InvalidFormatException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("valor_invalido", ex.getValue().toString());
        return new ResponseApi( "Por favor, verifique os dados fornecidos e tente novamente.", error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseApi handleNullPointer(HttpServletRequest request,
                                         NullPointerException ex) {
        logger.error(ex.getMessage());
        logger.error(ex.getLocalizedMessage());
        return new ResponseApi( "Não foi possivel processar o seu pedido. Por favor, tente novamente mais tarde. ", null);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ResponseApi handleConflits(ConflictException e){
        return new ResponseApi( e.getMessage(), null);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, String> handleUserNotFound(UsernameNotFoundException e){
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseApi handleModelNotFound(NotFoundException e){
        return new ResponseApi( e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseApi handleUnprocessableEntity(UnprocessableEntityException e){
        return new ResponseApi( e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseApi handleBadRequest(BadRequestException e){
        return new ResponseApi( e.getMessage(), null);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseApi handleHttpNotReadableExeption(HttpMessageNotReadableException e){
        return new ResponseApi( "Verifique o formato dos dados eviados", null);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseApi handllegalArgumentException(IllegalArgumentException e){
        return new ResponseApi( "Verifique os dados eviados", null);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseApi handleForbidden(HttpServletRequest request, ForbiddenException ex) {
        return new ResponseApi( ex.getMessage(),  null);
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseApi handleUnauthorized(HttpServletRequest request, UnauthorizedException ex) {
        return new ResponseApi( ex.getMessage(),  null);
    }




}
