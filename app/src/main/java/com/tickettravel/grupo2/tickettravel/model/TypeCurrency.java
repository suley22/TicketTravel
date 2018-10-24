package com.tickettravel.grupo2.tickettravel.model;

public class TypeCurrency {

    private int CurrencyId;
    private String Description;

    public int getCurrencyId() {
        return CurrencyId;
    }

    public void setCurrencyId(int currencyId) {
        CurrencyId = currencyId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return Description;
    }
}
