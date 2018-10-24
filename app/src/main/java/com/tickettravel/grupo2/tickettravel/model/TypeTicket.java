package com.tickettravel.grupo2.tickettravel.model;

public class TypeTicket {

    private int TypeId;
    private String Description;

    public int getTypeId() {
        return TypeId;
    }

    public void setTypeId(int typeId) {
        TypeId = typeId;
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
