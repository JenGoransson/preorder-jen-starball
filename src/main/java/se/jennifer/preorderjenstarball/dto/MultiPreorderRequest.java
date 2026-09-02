package se.jennifer.preorderjenstarball.dto;

import java.util.List;

public class MultiPreorderRequest {
    private String customerName;
    private String customerEmail;
    private List<PreorderItem> items;

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public List<PreorderItem> getItems() { return items; }
    public void setItems(List<PreorderItem> items) { this.items = items; }
}
