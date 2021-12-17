package com.bharath.springcloud.couponservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bharath.springcloud.couponservice.controller.CouponRestController;
import com.bharath.springcloud.couponservice.model.Coupon;
import com.bharath.springcloud.couponservice.repos.CouponRepo;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
// @SpringBootTest -> starts up application context to be used in a test
@SpringBootTest
class CouponserviceApplicationTests {

  private static final String COUPON_CODE = "moin";

  private static final BigDecimal DISCOUNT_FOR_COUPON_MOIN = BigDecimal.valueOf(10.5);

  // @Mock marks the field to be mocked
  @Mock
  private CouponRepo couponRepo;

  private CouponRestController couponRestController;

  // @BeforeEach marks this method to be called before each test method once
  @BeforeEach
  public void setup() {
    this.couponRestController = new CouponRestController(this.couponRepo);
  }

  // @Test marks the method as a test method
  @Test
  public void testCreate() {
    Coupon coupon = new Coupon();
    coupon.setCode(COUPON_CODE);

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.couponRepo.save(coupon)).thenReturn(coupon);

    Coupon createdCoupon = this.couponRestController.create(coupon);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.couponRepo).save(coupon);

    // checks whether the value is null or not
    assertNotNull(createdCoupon);

    // checks whether the expected and given value are the "same"
    assertEquals(COUPON_CODE, createdCoupon.getCode());
  }

  // @Test marks the method as a test method
  @Test
  public void testCreate_WhenCouponNull_ShouldThrowException() {

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(IllegalArgumentException.class, () -> {
      this.couponRestController.create(null);
    });
  }

  // @Test marks the method as a test method
  @Test
  public void testGetCoupon() {
    Coupon coupon = new Coupon();
    coupon.setCode(COUPON_CODE);
    coupon.setDiscount(DISCOUNT_FOR_COUPON_MOIN);

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.couponRepo.findByCode(COUPON_CODE)).thenReturn(coupon);

    Coupon foundCoupon = this.couponRestController.getCoupon(COUPON_CODE);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.couponRepo).findByCode(COUPON_CODE);

    // checks whether the value is null or not
    assertNotNull(foundCoupon);

    // checks whether the expected and given value are the "same"
    assertEquals(coupon, foundCoupon);
    assertEquals(coupon.getCode(), foundCoupon.getCode());
    assertEquals(DISCOUNT_FOR_COUPON_MOIN, foundCoupon.getDiscount());
  }

  // @Test marks the method as a test method
  @Test
  public void testGetCoupon_WhenCodeNull_ShouldThrowException() {

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(IllegalArgumentException.class, () -> {
      this.couponRestController.getCoupon(null);
    });
  }

}
