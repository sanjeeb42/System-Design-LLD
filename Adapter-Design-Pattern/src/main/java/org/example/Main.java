package org.example;

import Adapter.IReport;
import Adapter.XmlDataProviderAdapter;
import ClientCode.Client;
import XmlDataProvider.XmlDataprovider;

public class Main {
    public static void main(String[] args) {

        XmlDataprovider xmlDataprovider=new XmlDataprovider();
        IReport report=new XmlDataProviderAdapter(xmlDataprovider);

        Client client=new Client(report);
        String data="Sanjeeb:52";
        System.out.println(client.getReport(data));

    }
}