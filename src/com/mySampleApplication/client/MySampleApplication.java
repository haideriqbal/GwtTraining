package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    private static final int REFRESH_INTERVAL = 5000; //time is in milliseconds
    private VerticalPanel mainPanel = new VerticalPanel();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private FlexTable stocksFlexTable = new FlexTable();
    private TextBox newSymbolTextBox = new TextBox();
    private Button addBtn = new Button("Add");
    private Label timeStampLabel = new Label();
    private ArrayList<String> stockList = new ArrayList<String>();
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        //setting up the table header
        stocksFlexTable.setText(0,0,"Symbol");
        stocksFlexTable.setText(0,1,"Price");
        stocksFlexTable.setText(0,2,"Change");
        stocksFlexTable.setText(0,3,"Remove");

        //setting up the bottom textbox and button in Horizontal Layout
        addPanel.add(newSymbolTextBox);
        addPanel.add(addBtn);

        //setting up the main vertical panel
        mainPanel.add(stocksFlexTable);
        mainPanel.add(addPanel);
        mainPanel.add(timeStampLabel);


        //associating the root panel with the main vertical panel
        RootPanel.get("stockList").add(mainPanel);
        newSymbolTextBox.setFocus(true);

        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                refreshWatchList();
            }
        };

        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

        addBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                addStock();
            }
        });

        newSymbolTextBox.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
                {
                    addStock();
                }
            }
        });

    }

    private void addStock()
    {
        final String symbolTxt = newSymbolTextBox.getText().toUpperCase().trim();
        int row = stocksFlexTable.getRowCount();
        Button removeStockBtn = new Button("x");
        newSymbolTextBox.setFocus(true);

        if(!symbolTxt.matches("^[0-9A-Z\\.]{1,10}$"))
        {
            Window.alert("'" + symbolTxt + "' is not a valid symbol.");
            newSymbolTextBox.selectAll();
            return;
        }

        if(stockList.contains(symbolTxt))
        {
            return;
        }
        else
        {
            //row = stocksFlexTable.getRowCount();
            stockList.add(symbolTxt);
            stocksFlexTable.setText(row,0,symbolTxt);

        }

        removeStockBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int removeIndex = stockList.indexOf(symbolTxt);
                stockList.remove(removeIndex);
                stocksFlexTable.removeRow(removeIndex+1);
            }
        });
        stocksFlexTable.setWidget(row,3,removeStockBtn);
        newSymbolTextBox.setText("");

        refreshWatchList();
    }

    private void refreshWatchList()
    {
        final double MAX_PRICE = 100.0;
        final double MAX_PRICE_CHANGE = 0.02;

        StockPrice[] prices = new StockPrice[stockList.size()];
        for (int i = 0; i < stockList.size(); i++) {
            double price = Random.nextDouble() * MAX_PRICE;
            double change = price * MAX_PRICE_CHANGE
                    * (Random.nextDouble() * 2.0 - 1.0);

            prices[i] = new StockPrice(stockList.get(i), price, change);
        }

        updateTable(prices);
    }

    void updateTable(StockPrice[] prices)
    {
        for (int i = 0; i < prices.length; i++) {
            updateTable(prices[i]);
        }

        DateTimeFormat dateFormat = DateTimeFormat.getFormat(
                DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
        timeStampLabel.setText("Last update : " + dateFormat.format(new Date()));
    }

    void updateTable(StockPrice stock)
    {
        if (!stockList.contains(stock.getSymbol())) {
            return;
        }

        int row = stockList.indexOf(stock.getSymbol()) + 1;

        String priceText = NumberFormat.getFormat("#,##0.00").format(stock.getPrice());
        NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
        String changeText = changeFormat.format(stock.getPriceChange());
        String changePercentText = changeFormat.format(stock.getChangePercent());

        stocksFlexTable.setText(row, 1, priceText);
        stocksFlexTable.setText(row, 2, changeText + " (" + changePercentText + "%)");
    }
}

