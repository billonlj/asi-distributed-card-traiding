package com.asi.dto;

import java.sql.Date;
import com.asi.dto.CardInstanceDto;

public class SaleDto {
    
    private double priceSale;
    private Date dateSale;
	private int idSale;
    private CardInstanceDto cardInstance;

    public double getPriceSale() {
        return this.priceSale;
    }

    public void setPriceSale(double priceSale) {
        this.priceSale = priceSale;
    }

    public Date getDateSale() {
        return this.dateSale;
    }

    public void setDateSale(Date dateSale) {
        this.dateSale = dateSale;
    }

    public int getIdSale() {
        return this.idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public CardInstanceDto getCardInstance() {
        return this.cardInstance;
    }

    public void setCardInstance(CardInstanceDto cardInstance) {
        this.cardInstance = cardInstance;
    }

}
