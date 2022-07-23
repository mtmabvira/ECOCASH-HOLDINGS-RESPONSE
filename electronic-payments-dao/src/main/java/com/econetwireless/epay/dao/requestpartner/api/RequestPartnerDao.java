package com.econetwireless.epay.dao.requestpartner.api;

import com.econetwireless.epay.domain.RequestPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tnyamakura on 17/3/2017.
 */

@Repository
public interface RequestPartnerDao extends JpaRepository<RequestPartner, Long> {
    RequestPartner findByCode(String code);
}
