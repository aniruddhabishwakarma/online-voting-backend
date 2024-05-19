package com.project.System.exception;

import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException ex) {
        CustomMessage errorResponse = new CustomMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception handlers
     * <p>
     * These methods handle exceptions thrown by the controller methods
     * and return a ResponseEntity with a message in the body.
     * The message is a CustomMessage object converted to a String.
     * </p>
     *
     * @param ex Exception object
     * @return ResponseEntity with a message in the body
     * @see CustomMessage
     * @see ResponseEntity
     */

//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
//        return ResponseEntity.badRequest().body(new CustomMessage(ex.getLocalizedMessage()));
//    }
    //MethodArgumentTypeMismatchException
    @ExceptionHandler({org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        CustomMessage errorResponse = new CustomMessage("Please check the type of the parameters you have passed");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle MethodArgumentNotValidException
     * <p>
     * This method handles MethodArgumentNotValidException thrown by the controller methods
     * and returns a ResponseEntity with a message in the body.
     * The message is a HashMap with field names as keys and error messages as values.
     * </p>
     *
     * @param ex Exception object
     * @return ResponseEntity with a message in the body
     * @see ResponseEntity
     * @see org.springframework.web.bind.MethodArgumentNotValidException
     * @see ExceptionHandler
     */
    @ExceptionHandler({org.springframework.web.bind.MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    //InvalidFormatException
    @ExceptionHandler({com.fasterxml.jackson.databind.exc.InvalidFormatException.class})
    public ResponseEntity<?> handleInvalidFormatException(com.fasterxml.jackson.databind.exc.InvalidFormatException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put(ex.getPath().get(0).getFieldName(), "Invalid format");
        return ResponseEntity.badRequest().body(errors);
    }

    // UsernameNotFoundException
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        CustomMessage errorResponse = new CustomMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    //IllegalArgumentException
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        CustomMessage errorResponse = new CustomMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //.MalformedJwtException
    @ExceptionHandler({io.jsonwebtoken.MalformedJwtException.class})
    public ResponseEntity<?> handleMalformedJwtException(io.jsonwebtoken.MalformedJwtException ex) {
        CustomMessage errorResponse = new CustomMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //HttpMessageNotReadableException
    @ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new CustomMessage("Please check the type of the parameters you have passed"));
    }

    //.UnexpectedTypeException
    @ExceptionHandler({UnexpectedTypeException.class})
    public ResponseEntity<?> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "Please check the type of the parameters you have passed");
//        errors.put("error", ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    //.SizeLimitExceededException
    @ExceptionHandler({org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException.class})
    public ResponseEntity<?> handleSizeLimitExceededException(org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "File size is too large");
//        errors.put("error", ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    //MaxUploadSizeExceededException
    @ExceptionHandler({org.springframework.web.multipart.MaxUploadSizeExceededException.class})
    public ResponseEntity<?> handleMaxUploadSizeExceededException(org.springframework.web.multipart.MaxUploadSizeExceededException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "File size is too large");
//        errors.put("error", ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    //ClassCastException
    @ExceptionHandler({java.lang.ClassCastException.class})
    public ResponseEntity<?> handleClassCastException(java.lang.ClassCastException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "something went wrong while casting in the server");
//        errors.put("error", ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
