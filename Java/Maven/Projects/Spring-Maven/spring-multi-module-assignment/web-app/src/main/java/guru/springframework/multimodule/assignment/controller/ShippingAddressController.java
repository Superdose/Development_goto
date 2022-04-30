package guru.springframework.multimodule.assignment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import guru.springframework.multimodule.assignment.model.ShippingAddress;

@RestController
public class ShippingAddressController {

  @GetMapping(path = "/")
  public ShippingAddress getShippingAddress() {
    ShippingAddress shippingAddress = new ShippingAddress();
    shippingAddress.setExtendedAddress("Extended Address");
    shippingAddress.setLocality("Leipzig");
    shippingAddress.setCountryName("Germany");
    shippingAddress.setPostalCode("04229");
    shippingAddress.setPostOfficeBox("Office Box");

    return shippingAddress;
  }

}
