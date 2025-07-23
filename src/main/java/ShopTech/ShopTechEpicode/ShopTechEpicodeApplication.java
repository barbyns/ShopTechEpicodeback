package ShopTech.ShopTechEpicode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ShopTech/ShopTechEpicode/model")
public class ShopTechEpicodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopTechEpicodeApplication.class, args);
	}
}