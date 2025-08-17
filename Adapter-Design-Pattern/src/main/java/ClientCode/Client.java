package ClientCode;

import Adapter.IReport;

public class Client {
    IReport report;

    public Client(IReport report){
        this.report=report;
    }

    public String getReport(String data){
        return report.getJsonData(data);
    }
}
