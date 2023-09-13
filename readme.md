# Metrodata Academy SIBKM DevSecOps Task 6: Custom Exception Handler

## Objective
- Implement a custom exception handler in the Order-Service repository.

## Solution
### 1. Project Setup
1. Clone the repository on the "lareza-farhan-wanaghi" branch and navigate to the working directory at `order-service/src/main/java/com/example/orderservice`. Ensure you have your credentials ready:

   ```bash
   git clone -b lareza-farhan-wanaghi https://github.com/SIBKM-DevSecOps/order-service.git
   cd order-service/src/main/java/com/example/orderservice
   ```

### 2. Custom Exception Handler Implementation
1. Create a folder to store scripts related to the custom exception handler:

   ```bash
   mkdir exception
   ```

2. Create a script to represent the custom exception data:

   ```bash
   nano exception/CustomException.java
   ```

   Copy and paste the following content:

   ```java
   package com.example.orderservice.exception;

   import lombok.Data;

   @Data
   public class CustomException extends RuntimeException {

       private String error; // ORDER_NOT_FOUND
       private int status; // 404/500/409

       public CustomException(String message, String error, int status) {
           super(message);
           this.error = error;
           this.status = status;
       }
   }
   ```

3. Create a model script that will be used as the DTO of the custom exception:

   ```bash
   nano model/ErrorMessage.java
   ```

   Copy and paste the following content:

   ```java
   package com.example.orderservice.model;

   import lombok.Builder;
   import lombok.Data;

   @Data
   @Builder
   public class ErrorMessage {

       private String message;
       private String error;
   }
   ```

4. Create a utility script to use the custom exception:

   ```bash
   nano exception/RestResponseExceptionHandler.java
   ```

   Copy and paste the following content:

   ```java
   package com.example.orderservice.exception;

   import com.example.orderservice.model.ErrorMessage;
   import org.springframework.http.HttpStatus;
   import org.springframework.http.ResponseEntity;
   import org.springframework.web.bind.annotation.ControllerAdvice;
   import org.springframework.web.bind.annotation.ExceptionHandler;
   import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

   @ControllerAdvice
   public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

       @ExceptionHandler(CustomException.class)
       public ResponseEntity<ErrorMessage> customException(CustomException customException) {
           return new ResponseEntity<>(ErrorMessage.builder()
                   .message(customException.getMessage())
                   .error(customException.getError())
                   .build(),
                   HttpStatus.valueOf(customException.getStatus()));
       }
   }
   ```

5. Adjust the exception handling in the `getById` API implementation to use the custom exception handler:

   ```bash
   nano service/OrderServiceImpl.java
   ```

   Copy and paste the following content:

   ```java
   // Import statements
   import com.metrodata.orderservice.exception.CustomException;

   // ...

   @Override
   public OrderResponse getById(long id) {
       Order order = orderRepository.findById(id)
           .orElseThrow(() -> new CustomException(
               "Order with the given id " + id + " was not found.",
               "ORDER_NOT_FOUND",
               404));
       return mappingOrderToOrderResponse(order);
   }

   // ...
   ```

### 3. Testing
1. Visit the application's `GetByID` endpoint with an unavailable ID. The result should display a message in the specified format from the custom exception handler.

   ![Screenshot 2023-09-13 at 07.31.51.png](_resources/Screenshot%202023-09-13%20at%2007.31.51.png)
   
### 4. Pushing the solution
1. Push the solution
	```
	git add .
	git commit -m "Implementing custom exception handler"
	git push -u origin lareza-farhan-wanaghi
	```