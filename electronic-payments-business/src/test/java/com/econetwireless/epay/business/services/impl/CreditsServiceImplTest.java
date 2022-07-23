package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.utils.TestUtility;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CreditsServiceImplTest extends TestCase {

    @Mock
    private SubscriberRequestDao requestDao;
    @Mock
    private ChargingPlatform platform;

    private CreditsService creditsService;
    private String partnerCode;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestDao.save(any(SubscriberRequest.class))).then(TestUtility.SUBSCRIBER_REQUEST);
        when(platform.enquireBalance(anyString(), anyString())).then(TestUtility.SUCCESSFUL_BALANCE_ENQUIRY);
        when(platform.creditSubscriberAccount(any(INCreditRequest.class))).then(TestUtility.SUCCESSFUL_CREDIT_REQUEST);
        partnerCode = "hot-recharge";
        this.creditsService = new CreditsServiceImpl(platform, requestDao);
    }

        @Test(expected = Exception.class)
        public void testCreditMethod() {
        final AirtimeTopupRequest airtimeTopupRequest = new AirtimeTopupRequest();
        airtimeTopupRequest.setPartnerCode(partnerCode);
        airtimeTopupRequest.setReferenceNumber("Ref0023");
        airtimeTopupRequest.setAmount(3);
        airtimeTopupRequest.setMsisdn("774695900");
        creditsService.credit(airtimeTopupRequest);

    }

}