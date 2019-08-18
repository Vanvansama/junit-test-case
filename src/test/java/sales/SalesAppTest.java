package sales;

import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {

//    @InjectMocks
//    private SalesApp salesApp;
//    @Mock
//    private SalesDao salesDao;

    @Test
    public void testGenerateReport() {

        SalesApp salesApp = spy(SalesApp.class);
        doReturn(true).when(salesApp).validateDate(any());
        doReturn(new ArrayList<>()).when(salesApp).filteredReportDataList(any(), anyBoolean());
        doReturn(new ArrayList<>()).when(salesApp).generateTempList(any(), anyInt());
        doReturn(new SalesActivityReport()).when(salesApp).generateReport(any(), any());

        salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

        verify(salesApp, times(1)).generateReport(any(), any());
    }
}
