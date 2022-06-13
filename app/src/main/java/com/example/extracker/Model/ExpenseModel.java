package com.example.extracker.Model;

public class ExpenseModel {
    private String category, notes, date, id, bd_name;
    private int amount;

    public ExpenseModel(String category, String notes, String date, String id, String bd_name, int amount) {
        this.category = category;
        this.notes = notes;
        this.date = date;
        this.id = id;
        this.bd_name = bd_name;
        this.amount = amount;
    }

    public ExpenseModel() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBd_name() {
        return bd_name;
    }

    public void setBd_name(String bd_name) {
        this.bd_name = bd_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
