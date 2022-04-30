package guru.springframework.springboot.demofromintellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private String firstName;
  private String lastName;
  private String email;

}
