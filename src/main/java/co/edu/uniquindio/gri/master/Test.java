package co.edu.uniquindio.gri.master;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
public class Test{

	 @Bean
	    public ThreadPoolTaskExecutor executor1() {
	        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
	        threadPoolTaskExecutor.setCorePoolSize(10);
	        threadPoolTaskExecutor.setMaxPoolSize(10);
	        threadPoolTaskExecutor.initialize();
	        return threadPoolTaskExecutor;
	    }

	    @Bean
	    public ThreadPoolTaskExecutor executor2() {
	        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
	        threadPoolTaskExecutor.setCorePoolSize(100);
	        threadPoolTaskExecutor.setMaxPoolSize(100);
	        threadPoolTaskExecutor.initialize();
	        return threadPoolTaskExecutor;
	    }
	    
}
