package nelsonapps.pucminas.tcc.test.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import nelsonapps.pucminas.tcc.service.ProductDataService;
import nelsonapps.pucminas.tcc.service.ReverseLogisticService;

@Configuration
@ComponentScan(basePackages={"nelsonapps.pucminas.tcc.service"},
               excludeFilters={@ComponentScan.Filter
               (type=FilterType.ASSIGNABLE_TYPE,classes={ReverseLogisticService.class}),
               @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes={ProductDataService.class})})
public class TestServicesConfigWithOutIntegration {

}
