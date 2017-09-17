package nelsonapps.pucminas.tcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:integration-config.xml"})
@EnableAutoConfiguration
@ComponentScan(basePackages={"nelsonapps.pucminas.tcc"})
/*excludeFilters={@Filter(type=FilterType.ASSIGNABLE_TYPE,classes=ReverseLogisticService.class),
		        @Filter(type=FilterType.ASPECTJ,pattern="nelson.pucminas.tcc.integration.*")})*/
public class Application{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
	
}