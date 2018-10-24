package com.tickettravel.grupo2.tickettravel.model;

public class Travel {

    private int IdTravel;
    private String Origin;
    private String Destiny;
    private String DateStart;
    private String DateReturn;
    private int Status;

    public int getIdTravel() {
        return IdTravel;
    }

    public void setIdTravel(int idTravel) {
        IdTravel = idTravel;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDestiny() {
        return Destiny;
    }

    public void setDestiny(String destiny) {
        Destiny = destiny;
    }

    public String getDateStart() {
        return DateStart;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public String getReturn() {
        return DateReturn;
    }

    public void setReturn(String dateReturn) {
        DateReturn = dateReturn;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
