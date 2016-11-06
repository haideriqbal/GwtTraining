package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    private VerticalPanel mainPanel = new VerticalPanel();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private FlexTable stocksFlexTable = new FlexTable();
    private TextBox newSymbolTextBox = new TextBox();
    private Button addBtn = new Button("Add");
    private Label timeStampLabel = new Label();
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

    }


}
