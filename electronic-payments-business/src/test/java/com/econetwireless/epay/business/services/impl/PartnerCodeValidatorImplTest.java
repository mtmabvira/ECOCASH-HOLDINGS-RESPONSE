package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.services.api.PartnerCodeValidator;
import com.econetwireless.epay.business.utils.TestUtility;
import com.econetwireless.epay.dao.requestpartner.api.RequestPartnerDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.execeptions.EpayException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class PartnerCodeValidatorImplTest extends TestCase {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Mock
    private RequestPartnerDao requestPartnerDao;
    private PartnerCodeValidator partnerCodeValidator;
    private String partnerCode;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestPartnerDao.findByCode(anyString())).then(TestUtility.REQUEST_PARTNER_ANSWER);
        partnerCodeValidator = new PartnerCodeValidatorImpl(requestPartnerDao);
        partnerCode = "hot-recharge";
    }
    @Test
    public void testNoneNullPartnerCode() {
        boolean validatePartnerCode = partnerCodeValidator.validatePartnerCode("Test");
        assertTrue(validatePartnerCode);
    }
    @Test(expected = Exception.class)
    public void testPartnerCode() {
        exception.expect(EpayException.class);
        partnerCodeValidator.validatePartnerCode(partnerCode);
    }
}