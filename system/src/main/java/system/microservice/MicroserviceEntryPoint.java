
package system.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MicroserviceEntryPoint {

	@RequestMapping("/")
	public String home() {
		return "Iesus Hominum Salvator <3";
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEntryPoint.class, args);
	}

}
