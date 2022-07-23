package com.econetwireless.epay.business.utils;

import com.econetwireless.epay.domain.RequestPartner;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.apache.commons.lang3.StringUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUtility {
    public static final Double BALANCE = 10.0;
    public static final Answer<SubscriberRequest> SUBSCRIBER_REQUEST = invocationOnMock -> {
        Object[] arguments = invocationOnMock.getArguments();
        if (arguments != null && arguments.length > 0) {
            SubscriberRequest request = (SubscriberRequest) arguments[0];
            if (request == null) {
                return null;
            }
            if (request.getId() == null) {
                request.setId(new Random().nextLong());
            }
            if (request.getDateCreated() == null) {
                request.setDateCreated(new Date());
            }
            request.setDateLastUpdated(new Date());
            return request;
        }
        return null;
    };
    public final static Answer<INBalanceResponse> SUCCESSFUL_BALANCE_ENQUIRY = invocationOnMock -> {
        Object[] arguments = invocationOnMock.getArguments();
        if (arguments != null && arguments.length > 1) {
            String partnerCode = (String) arguments[0];
            String msisdn = (String) arguments[1];
            INBalanceResponse balanceResponse = new INBalanceResponse();
            if (StringUtils.isEmpty(partnerCode)) {
                balanceResponse.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
                balanceResponse.setNarrative("Invalid request, missing partner code");
                return balanceResponse;
            }
            if (StringUtils.isEmpty(msisdn)) {
                balanceResponse.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
                balanceResponse.setNarrative("Invalid request, missing mobile number");
                return balanceResponse;
            }
            balanceResponse.setMsisdn(msisdn);
            balanceResponse.setAmount(100);
            balanceResponse.setNarrative("Successful balance enquiry");
            balanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
            return balanceResponse;
        }
        return null;
    };
    public static final Answer<INCreditResponse> SUCCESSFUL_CREDIT_REQUEST = invocation -> {
        Object[] arguments = invocation.getArguments();
        if (arguments != null && arguments.length > 0) {
            INCreditRequest creditRequest = (INCreditRequest) arguments[0];
            final INCreditResponse creditResponse = new INCreditResponse();
            if (creditRequest == null) {
                creditResponse.setResponseCode(ResponseCode.FAILED.getCode());
                creditResponse.setNarrative("Invalid request, empty credit request");
                return creditResponse;
            }
            if (StringUtils.equalsIgnoreCase(creditRequest.getMsisdn(), "SHOULD_SEND_NULL_STATUS")) {
                creditResponse.setResponseCode(null);
                creditResponse.setNarrative("Invalid request, empty credit request");
                return creditResponse;
            }

            creditResponse.setMsisdn(creditRequest.getMsisdn());
            creditResponse.setBalance(creditRequest.getAmount() + BALANCE);
            creditResponse.setNarrative("Successful credit request");
            creditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
            return creditResponse;
        }
        return null;
    };
    public static final Answer<RequestPartner> REQUEST_PARTNER_ANSWER = invocation -> {
        Object[] arguments = invocation.getArguments();
        if (arguments != null && arguments.length > 0) {
            String partnerCode = (String) arguments[0];
            if (partnerCode != null) {
                RequestPartner partner = new RequestPartner();
                partner.setCode(partnerCode);
                partner.setName(UUID.randomUUID().toString());
                partner.setDescription("my_description");
                partner.setId(new Random().nextLong());
                return partner;
            }
            return null;
        }
        return null;
    };
    public final static Answer<SubscriberRequest> SUBSCRIBER_REQUEST_ANSWER = invocation -> {
        Object[] arguments = invocation.getArguments();
        if (arguments != null && arguments.length > 0) {
            SubscriberRequest request = (SubscriberRequest) arguments[0];
            if (request == null) return null;
            if (request.getId() == null) {
                request.setId(new Random().nextLong());
            }
            if (request.getDateCreated() == null) {
                request.setDateCreated(new Date());
            }
            request.setDateLastUpdated(new Date());
            return request;
        }
        return null;
    };
    public final static Answer<BalanceResponse> BALANCE_RESPONSE_ANSWER = invocation -> {
        Object[] arguments = invocation.getArguments();
        if (arguments != null && arguments.length > 1) {
            String partnerCode = (String) arguments[0];
            String msisdn = (String) arguments[1];
            final BalanceResponse balanceResponse = new BalanceResponse();
            if (StringUtils.isEmpty(partnerCode)) {
                balanceResponse.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
                balanceResponse.setNarrative("Invalid request, missing partner code");
                return balanceResponse;
            }
            if (StringUtils.isEmpty(msisdn)) {
                balanceResponse.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
                balanceResponse.setNarrative("Invalid request, missing mobile number");
                return balanceResponse;
            }
            balanceResponse.setMsisdn(msisdn);
            balanceResponse.setAmount(BALANCE);
            balanceResponse.setNarrative("Successful balance enquiry");
            balanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
            return balanceResponse;
        }
        return null;
    };

    public static final Answer<CreditResponse> CREDIT_RESPONSE_ANSWER = new Answer<CreditResponse>() {
        @Override
        public CreditResponse answer(InvocationOnMock invocation) throws Throwable {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0) {
                CreditRequest creditRequest = (CreditRequest) arguments[0];
                final CreditResponse creditResponse = new CreditResponse();
                if (creditRequest == null) {
                    creditResponse.setResponseCode(ResponseCode.FAILED.getCode());
                    creditResponse.setNarrative("Invalid request, empty credit request");
                    return creditResponse;
                }
                creditResponse.setMsisdn(creditRequest.getMsisdn());
                creditResponse.setBalance(creditRequest.getAmount() + BALANCE);
                creditResponse.setNarrative("Successful credit request");
                creditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
                return creditResponse;
            }
            return null;
        }
    };
    public static List<SubscriberRequest> SUBSCRIBER_REQUESTS = new ArrayList<>();

    static {
        List<String> partners = new ArrayList<>(Arrays.asList("A", "B", "C", "C", "D"));
        partners.forEach((partner) -> {
            SubscriberRequest request = new SubscriberRequest();
            request.setDateLastUpdated(new Date());
            request.setDateCreated(new Date());
            request.setId(new Random().nextLong());
            request.setPartnerCode(partner);
            request.setBalanceAfter(new Random().nextDouble());
            request.setBalanceBefore(new Random().nextDouble());
            request.setAmount(new Random().nextDouble());
            SUBSCRIBER_REQUESTS.add(request);
        });

    }

    public static void shouldBeSame(INBalanceResponse expected, INBalanceResponse actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getResponseCode(), actual.getResponseCode());
        assertEquals(expected.getAmount(), actual.getAmount(), 0.001);
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
    }


}
