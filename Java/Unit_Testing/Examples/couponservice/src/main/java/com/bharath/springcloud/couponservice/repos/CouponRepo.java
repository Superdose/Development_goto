package com.bharath.springcloud.couponservice.repos;

import com.bharath.springcloud.couponservice.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

  Coupon findByCode(String code);

}
