package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.services.api.ReportingService;
import com.econetwireless.epay.business.utils.TestUtility;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ReportingServiceImplTest extends TestCase {
    private ReportingService reportingService;

    @Mock
    private SubscriberRequestDao subscriberRequestDao;
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(subscriberRequestDao.save(any(SubscriberRequest.class))).then(TestUtility.SUBSCRIBER_REQUEST);
        reportingService = new ReportingServiceImpl(subscriberRequestDao);
    }
    @Test
    public void testNullPartnerCode() {
        List<SubscriberRequest> subscriberRequests = reportingService.findSubscriberRequestsByPartnerCode(null);
        assertNotNull(subscriberRequests);
        assertEquals(0, subscriberRequests.size());
    }
    @Test
    public void testFindSubscriberRequestsByPartnerCode(){
        List<SubscriberRequest> subscriberRequests = reportingService.findSubscriberRequestsByPartnerCode("hot-recharge");
        assertNotNull(subscriberRequests);
        assertEquals(0, subscriberRequests.size());
    }
}