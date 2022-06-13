package com.example.extracker.Model;

public class BudgetModel {
    private String bd_name,fromDate,toDate,id;
    private int amount;

    public BudgetModel(String bd_name, String fromDate, String toDate, String id, int amount) {
        this.bd_name = bd_name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.id = id;
        this.amount = amount;
    }

    public BudgetModel() {
    }

    public String getBd_name() {
        return bd_name;
    }

    public void setBd_name(String bd_name) {
        this.bd_name = bd_name;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
