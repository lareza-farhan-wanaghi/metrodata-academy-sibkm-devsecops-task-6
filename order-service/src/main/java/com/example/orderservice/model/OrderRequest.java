package com.example.orderservice.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private int quantity;
    private long amount;
    private String status;
    private long productId;

}
