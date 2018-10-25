package com.tickettravel.grupo2.tickettravel.model;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;

@Table
public class Ticket extends SugarRecordModel {
    private static final long serialVersionUID = 5530255968065458983L;

    private @SerializedName("Amount")
    float Amount;
    private @SerializedName("Observation")
    String Observation;
    private String TicketTypeDescription;
    private @SerializedName("TicketTypeId")
    int TicketTypeId;
    private @SerializedName("TypeCurrencyId")
    int TypeCurrencyId;
    private String TypeCurrencyDescription;
    private String Geolocation;
    private @SerializedName("Date")
    String Date;
    private @SerializedName("ImageUrl")
    String ImageUrl;
    private @SerializedName("IdTravel")
    int IdTravel;

    public Ticket(int amount, String observation, String ticketTypeName) {

        this.Amount = amount;
        this.Observation = observation;
        this.TicketTypeDescription = ticketTypeName;
    }

    public Ticket(){}

    public Ticket(float amount, int typeTicket, String TypeDescription, String date, String geolocation, int typeCurrency, String CurrencyDescription, String observation, String imageUrl) {

        this.Amount = amount;
        this.TicketTypeId = typeTicket;
        this.TicketTypeDescription = TypeDescription;
        this.Date = date;
        this.Geolocation = geolocation;
        this.TypeCurrencyId = typeCurrency;
        this.TypeCurrencyDescription = CurrencyDescription;
        this.Observation = observation;
        this.ImageUrl = imageUrl;
    }

    public int getIdTravel() {
        return IdTravel;
    }

    public void setIdTravel(int idTravel) {
        IdTravel = idTravel;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public String getTicketTypeDescription() {
        return TicketTypeDescription;
    }

    public void setTicketTypeDescription(String ticketTypeDescription) {
        TicketTypeDescription = ticketTypeDescription;
    }

    public int getTicketTypeId() {
        return TicketTypeId;
    }

    public void setTicketTypeId(int ticketTypeId) {
        TicketTypeId = ticketTypeId;
    }

    public int getTypeCurrencyId() {
        return TypeCurrencyId;
    }

    public void setTypeCurrencyId(int typeCurrencyId) {
        TypeCurrencyId = typeCurrencyId;
    }

    public String getTypeCurrencyDescription() {
        return TypeCurrencyDescription;
    }

    public void setTypeCurrencyDescription(String typeCurrencyDescription) {
        TypeCurrencyDescription = typeCurrencyDescription;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getGeolocation() {
        return Geolocation;
    }

    public void setGeolocation(String geolocation) {
        Geolocation = geolocation;
    }
}
