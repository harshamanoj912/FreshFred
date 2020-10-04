package com.teamevox.freshfred.IT19216492;

public class RequestSupplyOrders {

    String supplierItemName, supplierOrderQuantity, supplierOrderAddress, supplierOrderMobile;

    public RequestSupplyOrders() {
    }

    public RequestSupplyOrders(String supplierItemName, String supplierOrderQuantity, String supplierOrderAddress, String supplierOrderMobile) {
        this.supplierItemName = supplierItemName;
        this.supplierOrderQuantity = supplierOrderQuantity;
        this.supplierOrderAddress = supplierOrderAddress;
        this.supplierOrderMobile = supplierOrderMobile;
    }

    @Override
    public String toString() {
        return "RequestSupplyOrders{" +
                "supplierItemName='" + supplierItemName + '\'' +
                ", supplierOrderQuantity='" + supplierOrderQuantity + '\'' +
                ", supplierOrderAddress='" + supplierOrderAddress + '\'' +
                ", supplierOrderMobile='" + supplierOrderMobile + '\'' +
                '}';
    }

    public String getSupplierItemName() {
        return supplierItemName;
    }

    public void setSupplierItemName(String supplierItemName) {
        this.supplierItemName = supplierItemName;
    }

    public String getSupplierOrderQuantity() {
        return supplierOrderQuantity;
    }

    public void setSupplierOrderQuantity(String supplierOrderQuantity) {
        this.supplierOrderQuantity = supplierOrderQuantity;
    }

    public String getSupplierOrderAddress() {
        return supplierOrderAddress;
    }

    public void setSupplierOrderAddress(String supplierOrderAddress) {
        this.supplierOrderAddress = supplierOrderAddress;
    }

    public String getSupplierOrderMobile() {
        return supplierOrderMobile;
    }

    public void setSupplierOrderMobile(String supplierOrderMobile) {
        this.supplierOrderMobile = supplierOrderMobile;
    }
}
