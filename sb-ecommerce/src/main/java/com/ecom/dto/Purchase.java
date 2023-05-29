package com.ecom.dto;

import com.ecom.entity.Address;
import com.ecom.entity.Customer;
import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
