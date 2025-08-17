package Adapter;

import XmlDataProvider.XmlDataprovider;

public class XmlDataProviderAdapter implements IReport{

    XmlDataprovider dataprovider;

    public XmlDataProviderAdapter(XmlDataprovider provider){
        this.dataprovider=provider;
    }

    @Override
    public String getJsonData(String data) {
        // 1. Get XML from the adaptee
        String xml = dataprovider.getXmlData(data);

        // 2. Naively parse out <name> and <id> values
        int startName = xml.indexOf("<name>") + 6;
        int endName   = xml.indexOf("</name>");
        String name   = xml.substring(startName, endName);

        int startId = xml.indexOf("<id>") + 4;
        int endId   = xml.indexOf("</id>");
        String id    = xml.substring(startId, endId);

        // 3. Build and return JSON
        return "{\"name\":\"" + name + "\", \"id\":" + id + "}";
    }
}
