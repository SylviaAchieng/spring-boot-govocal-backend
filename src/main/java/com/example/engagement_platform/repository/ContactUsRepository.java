package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ContactUsRepository extends JpaRepository<ContactUs, BigDecimal> {
}
