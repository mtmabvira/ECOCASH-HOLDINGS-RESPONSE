package com.econetwireless.epay.dao.subscriberrequest.api;

import com.econetwireless.epay.domain.SubscriberRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tnyamakura on 17/3/2017.
 */

@Repository
public interface SubscriberRequestDao extends JpaRepository<SubscriberRequest, Long> {
    List<SubscriberRequest> findByPartnerCode(@Param("partnerCode") String partnerCode);
}
