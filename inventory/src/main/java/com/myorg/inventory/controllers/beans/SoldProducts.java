package com.myorg.inventory.controllers.beans;

import javax.validation.constraints.*;

public class SoldProducts {

    @NotBlank
    @Min(1)
    private Integer quantitySold;
    @NotBlank
    @Size(min=1)
    private String artNumber;

    public SoldProducts(Integer quantitySold, String artNumber) {
        this.quantitySold = quantitySold;
        this.artNumber = artNumber;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(String artNumber) {
        this.artNumber = artNumber;
    }
}
