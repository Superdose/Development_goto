package guru.springframework.multimodule.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "guru.springframework.multimodule.assignment")
public class SpringMultiModuleAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMultiModuleAssignmentApplication.class, args);
	}

}
