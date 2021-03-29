package com.myorg.inventory.controllers.beans;

import org.hibernate.annotations.NotFound;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class SoldProductsOrderDetails {

    @NotNull
    private Integer orderId;
    @NotBlank
    private String orderNumber;

    @Size(min = 1) // the size consideration is ZERO because there can be a selleable article/product with no child articles
    List<SoldProducts> soldProduclsList = new ArrayList<>();

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<SoldProducts> getSoldProduclsList() {
        return soldProduclsList;
    }

    public void setSoldProduclsList(List<SoldProducts> soldProduclsList) {
        this.soldProduclsList = soldProduclsList;
    }
}
