package com.teamevox.freshfred.IT19208718;

public class OrderListDisplayForRiderModel {

    public String address, customerName, foodName, mobileNumber, orderId, totalPrice;

    public OrderListDisplayForRiderModel() {

    }

    public OrderListDisplayForRiderModel( String customerName, String address,String foodName, String mobileNumber, String orderId, String totalPrice) {
        this.address = address;
        this.customerName = customerName;
        this.foodName = foodName;
        this.mobileNumber = mobileNumber;
        this.orderId = orderId;
        this.totalPrice = totalPrice;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
