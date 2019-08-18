package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

        SalesDao salesDao = new SalesDao();
        SalesReportDao salesReportDao = new SalesReportDao();
        List<String> headers = null;

        if (salesId == null) {
            return;
        }

        Sales sales = salesDao.getSalesBySalesId(salesId);

        if (!validateDate(sales)) {
            return;
        }

        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

        List<SalesReportData> filteredReportDataList = filteredReportDataList(reportDataList, isSupervisor);

        filteredReportDataList = generateTempList(reportDataList, maxRow);

        if (isNatTrade) {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        } else {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
        }

        SalesActivityReport report = this.generateReport(headers, reportDataList);

        EcmService ecmService = new EcmService();
        ecmService.uploadDocument(report.toXml());

    }

    protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

    protected boolean validateDate(Sales sales) {
        Date today = new Date();
        return !today.after(sales.getEffectiveTo()) && !today.before(sales.getEffectiveFrom());
    }

    protected List<SalesReportData> filteredReportDataList(List<SalesReportData> reportDataList, boolean isSupervisor) {
        List<SalesReportData> result = new ArrayList<>();
        for (SalesReportData data : reportDataList) {
            if ("SalesActivity".equalsIgnoreCase(data.getType())) {
                if (data.isConfidential()) {
                    if (isSupervisor) {
                        result.add(data);
                    }
                } else {
                    result.add(data);
                }
            }
        }
        return result;
    }

    protected List<SalesReportData> generateTempList(List<SalesReportData> reportDataList, int maxRow) {
        List<SalesReportData> tempList = new ArrayList<>();
        for (int i = 0; i < reportDataList.size() || i < maxRow; i++) {
            tempList.add(reportDataList.get(i));
        }
        return tempList;
    }
}
