package com.teamevox.freshfred.IT19216492;

public class Supplier {
    String supplierName, supplierNic , supplierMobile, supplierItem, supplierPassword;

    public Supplier(){

    }


    public Supplier(String supplierName, String supplierNic, String supplierMobile, String supplierItem, String supplierPassword){
        this.supplierName = supplierName;
        this.supplierNic = supplierNic;
        this.supplierMobile = supplierMobile;
        this.supplierItem = supplierItem;
        this.supplierPassword = supplierPassword;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierPassword='" + supplierPassword + '\'' +
                ", supplierNic='" + supplierNic + '\'' +
                ", supplierMobile='" + supplierMobile + '\'' +
                ", supplierItem='" + supplierItem + '\'' +
                ", supplierPassword='" + supplierPassword + '\'' +
                '}';
    }


    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierNic(String supplierNic) {
        this.supplierNic = supplierNic;
    }

    public void setSupplierMobile(String supplierMobile) {
        this.supplierMobile = supplierMobile;
    }

    public void setSupplierItem(String supplierItem) {
        this.supplierItem = supplierItem;
    }

    public void setSupplierPassword(String supplierPassword) {
        this.supplierPassword = supplierPassword;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierNic() {
        return supplierNic;
    }

    public String getSupplierMobile() {
        return supplierMobile;
    }

    public String getSupplierItem() {
        return supplierItem;
    }

    public String getSupplierPassword() {
        return supplierPassword;
    }

}




