package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.utils.TestUtility;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class EnquiriesServiceImplTest extends TestCase {
    private EnquiriesServiceImpl enquiriesServiceImpl;
    @Mock
    private ChargingPlatform chargingPlatform;
    @Mock
    private SubscriberRequestDao requestDao;

    SubscriberRequest subscriberRequest;

    INBalanceResponse inBalanceResponse;

    private String partnerCode;

    @Mock
    private SubscriberRequestDao subscriberRequestDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestDao.save(any(SubscriberRequest.class))).then(TestUtility.SUBSCRIBER_REQUEST_ANSWER);
        when(chargingPlatform.enquireBalance(anyString(), anyString())).then(TestUtility.SUCCESSFUL_BALANCE_ENQUIRY);
        this.enquiriesServiceImpl = new EnquiriesServiceImpl(chargingPlatform, requestDao);
        partnerCode = "hot-recharge";
    }

    @Test
    public void testEnquire(){
        AirtimeBalanceResponse  airtimeBalanceResponse =  this.enquiriesServiceImpl.enquire(partnerCode, "774695900");
        assertNotNull(airtimeBalanceResponse);
        assertEquals("200", airtimeBalanceResponse.getResponseCode());
    }
}