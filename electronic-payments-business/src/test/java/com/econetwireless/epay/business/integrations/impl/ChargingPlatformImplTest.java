package com.econetwireless.epay.business.integrations.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.utils.TestUtility;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.IntelligentNetworkService;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.pojo.INBalanceResponse;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.econetwireless.epay.business.utils.TestUtility.shouldBeSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ChargingPlatformImplTest extends TestCase {
    private ChargingPlatform platform;

    @Mock
    private IntelligentNetworkService intelligentNetworkService;
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(intelligentNetworkService.enquireBalance(anyString(), anyString())).then(TestUtility.BALANCE_RESPONSE_ANSWER);
        when(intelligentNetworkService.creditSubscriberAccount(any(CreditRequest.class))).then(TestUtility.CREDIT_RESPONSE_ANSWER);
        platform = new ChargingPlatformImpl(intelligentNetworkService);
    }
    @Test
    public void testMissingPartnerCode() {
        INBalanceResponse expected = new INBalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing partner code");
        INBalanceResponse actual = platform.enquireBalance(null, null);
        shouldBeSame(expected, actual);

    }

    @Test
    public void testMissingMobileNumber() {
        INBalanceResponse expected = new INBalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing mobile number");
        INBalanceResponse actual = platform.enquireBalance("hello", null);
        shouldBeSame(expected, actual);
    }
}