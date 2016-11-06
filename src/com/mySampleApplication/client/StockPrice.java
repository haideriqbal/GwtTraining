package com.mySampleApplication.client;

/**
 * Created by Haider on 11/7/2016.
 */
public class StockPrice {
    private String symbol;
    private double price;
    private double priceChange;

    StockPrice(){}

    StockPrice(String sym, double _price, double _priceChange)
    {
        this.price = _price;
        this.symbol = sym;
        this.priceChange = _priceChange;
    }

    String getSymbol()
    {
        return this.symbol;
    }

    double getPrice()
    {
        return this.price;
    }

    double getPriceChange()
    {
        return this.priceChange;
    }

    void setSymbol(String _symbol)
    {
        symbol = _symbol;
    }

    void setPrice (double _price)
    {
        price = _price;
    }

    void setPriceChange(double _priceChange)
    {
        priceChange = _priceChange;
    }

    double getChangePercent()
    {
        return 10.0 * this.priceChange/this.price;
    }
}
