package com.bharath.springcloud.productservice;

import com.bharath.springcloud.productservice.controller.ProductRestController;
import com.bharath.springcloud.productservice.dto.Coupon;
import com.bharath.springcloud.productservice.model.Product;
import com.bharath.springcloud.productservice.repos.ProductRepo;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
// @SpringBootTest -> starts up application context to be used in a test
@SpringBootTest
class ProductserviceApplicationTests {

  private static final String COUPON_URL = "http://localhost:9091/couponapi/coupons";

  private static final String COUPON_CODE = "moin";

  private static final BigDecimal DISCOUNT_FOR_COUPON_MOIN = BigDecimal.valueOf(10.5);

  private static final BigDecimal PRICE_FOR_PRODUCT = BigDecimal.valueOf(2000.0);

  private static final BigDecimal EXPECTED_PRICE_AFTER_DISCOUNT = BigDecimal.valueOf(1989.5);

  // @Mock marks the field to be mocked
  @Mock
  private ProductRepo productRepo;

  // @Mock marks the field to be mocked
  @Mock
  private RestTemplate restTemplate;

  private ProductRestController productRestController;

  // @BeforeEach marks this method to be called before each test method once
  @BeforeEach
  public void setup() {
    this.productRestController = new ProductRestController(
        this.productRepo,
        this.restTemplate,
        COUPON_URL
    );
  }

  // @Test marks the method as a test method
  @Test
  public void testCreate_ShouldReturnProduct() {
    Product product = new Product();
    Coupon coupon = new Coupon();

    String finalCouponUrl = this.setupProductAndCouponAndCouponUrl(product, coupon);

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(
        this.restTemplate.getForObject(
            finalCouponUrl,
            Coupon.class
        )
    ).thenReturn(coupon);

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.productRepo.save(product)).thenReturn(product);

    Product returnedProduct = this.productRestController.create(product);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.restTemplate).getForObject(
        finalCouponUrl,
        Coupon.class
    );

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.productRepo).save(product);

    // checks whether the value is null or not
    assertNotNull(returnedProduct);
    assertNotNull(returnedProduct.getPrice());

    // checks whether the expected and given value are the "same"
    assertEquals(EXPECTED_PRICE_AFTER_DISCOUNT, returnedProduct.getPrice());

  }

  // @Test marks the method as a test method
  @Test
  public void testCreate_NoCouponServiceUrl_ShouldThrowException() {
    this.productRestController = new ProductRestController(
        this.productRepo,
        this.restTemplate,
        null
    );

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(NullPointerException.class, () -> {
      this.productRestController.create(new Product());
    });
  }

  // @Test marks the method as a test method
  @Test
  public void testCreate_NoCouponReturned_ShouldThrowException() {
    Product product = new Product();
    Coupon coupon = new Coupon();

    String finalCouponUrl = this.setupProductAndCouponAndCouponUrl(product, coupon);

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(
        this.restTemplate.getForObject(
            finalCouponUrl,
            Coupon.class
        )
    ).thenReturn(null);

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(NullPointerException.class, () -> {
      this.productRestController.create(product);
    });

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.restTemplate).getForObject(finalCouponUrl, Coupon.class);
  }

  private String setupProductAndCouponAndCouponUrl(
      Product product,
      Coupon coupon
  ) {
    product.setCouponCode(COUPON_CODE);
    product.setPrice(PRICE_FOR_PRODUCT);

    coupon.setDiscount(DISCOUNT_FOR_COUPON_MOIN);

    return COUPON_URL + "/" + product.getCouponCode();
  }

}
