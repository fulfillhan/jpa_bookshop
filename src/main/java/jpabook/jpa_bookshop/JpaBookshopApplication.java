package jpabook.jpa_bookshop;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaBookshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBookshopApplication.class, args);

	}


	/*
	기본적으로 초기화된 프록시만 노출, 초기화 되지 않은 프록시 노출 안함
	*/
	@Bean
	Hibernate5JakartaModule hibernate5JakartaModule(){
		return new Hibernate5JakartaModule();
	}

/*
	 json 생성시 강제로 지연 로딩을 하려면

	@Bean
	Hibernate5JakartaModule hibernate5JakartaModule(){
		Hibernate5JakartaModule module = new Hibernate5JakartaModule();
		//강제로 지연 로딩
		module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
		return module;
	}
*/


}
