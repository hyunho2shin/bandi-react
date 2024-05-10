package bandi.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan( basePackages ={ "bandi.react.mine"})
@SpringBootApplication
public class BandiReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(BandiReactApplication.class, args);
	}

}
