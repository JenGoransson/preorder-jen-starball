package se.jennifer.preorderjenstarball.dto;

import java.util.List;

public class MultiPreorderRequest {

    private String customerName;
    private String customerEmail;

    private String street;
    private String postalCode;
    private String city;

    private List<PreorderItem> items;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<PreorderItem> getItems() {
        return items;
    }

    public void setItems(List<PreorderItem> items) {
        this.items = items;
    }

}

