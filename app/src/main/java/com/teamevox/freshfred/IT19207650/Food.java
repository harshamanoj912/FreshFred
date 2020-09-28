package com.teamevox.freshfred.IT19207650;

public class Food {
    String foodID;
    String foodName;
    String foodDes;
    String foodPrice;

    public Food() {

    }

    public Food(String foodID, String foodName, String foodDes, String foodPrice) {

        this.foodID = foodID;
        this.foodName = foodName;
        this.foodDes = foodDes;
        this.foodPrice = foodPrice;
    }


    public String getFoodID() {

        return foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodDes() {
        return foodDes;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

}