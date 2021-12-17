package com.bharath.springcloud.productservice.controller;

import com.bharath.springcloud.productservice.dto.Coupon;
import com.bharath.springcloud.productservice.model.Product;
import com.bharath.springcloud.productservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {

  private static final String PRODUCT_URL = "/products";

  private final String couponServiceUrl;

  private final ProductRepo productRepo;

  private final RestTemplate restTemplate;

  @Autowired
  public ProductRestController(
      ProductRepo productRepo,
      RestTemplate restTemplate,
      @Value("${couponService.url}") String couponServiceUrl
  ) {
    this.productRepo = productRepo;
    this.restTemplate = restTemplate;
    this.couponServiceUrl = couponServiceUrl;
  }

  @PostMapping(path = ProductRestController.PRODUCT_URL)
  public Product create(@RequestBody Product product) {

    if(this.couponServiceUrl == null)
      throw new NullPointerException("couponServiceUrl is null");

    if(product.getCouponCode() != null) {
      Coupon coupon = this.restTemplate.getForObject(
          this.couponServiceUrl + "/" + product.getCouponCode(),
          Coupon.class
      );

      if(coupon == null)
        throw new NullPointerException("coupon was not found, even tho there should be one");

      product.setPrice(product.getPrice().subtract(coupon.getDiscount()));

    }

    return this.productRepo.save(product);
  }

}
