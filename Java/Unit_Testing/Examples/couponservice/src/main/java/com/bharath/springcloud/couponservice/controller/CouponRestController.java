package com.bharath.springcloud.couponservice.controller;

import com.bharath.springcloud.couponservice.model.Coupon;
import com.bharath.springcloud.couponservice.repos.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponapi")
public class CouponRestController {

  private static final String COUPON_URL = "/coupons";

  private final CouponRepo couponRepo;

  @Autowired
  public CouponRestController(CouponRepo couponRepo) {
    this.couponRepo = couponRepo;
  }

  @GetMapping(path = CouponRestController.COUPON_URL + "/{code}")
  public Coupon getCoupon(@PathVariable String code) {
    if(code == null) throw new IllegalArgumentException("Given code should not be null");

    return this.couponRepo.findByCode(code);
  }

  @PostMapping(path = CouponRestController.COUPON_URL)
  public Coupon create(@RequestBody Coupon coupon) {
    if(coupon == null) throw new IllegalArgumentException("Given coupon should not be null");

    return this.couponRepo.save(coupon);
  }

}
