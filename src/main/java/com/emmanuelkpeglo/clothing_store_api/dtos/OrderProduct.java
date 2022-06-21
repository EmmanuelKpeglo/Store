package com.emmanuelkpeglo.clothing_store_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    long productId;
    int quantity;
}
