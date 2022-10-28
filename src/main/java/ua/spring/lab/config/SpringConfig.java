package ua.spring.lab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@ComponentScan("ua.spring.lab")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
//    @Bean
//    public ViewResolver viewResolver(){
//        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
//        thymeleafViewResolver.setTemplateEngine((ISpringTemplateEngine) templateEngine());
//        thymeleafViewResolver.setCharacterEncoding("UTF-8");
//        return thymeleafViewResolver;
//    }
//
//    @Bean
//    public TemplateEngine templateEngine(){
//        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
//        springTemplateEngine.setEnableSpringELCompiler(true);
//        springTemplateEngine.setTemplateResolver(templateResolver());
//        return springTemplateEngine;
//    }
//
//    @Bean
//    public ITemplateResolver templateResolver(){
//        SpringResourceTemplateResolver springResourceTemplateResolver = new
//                SpringResourceTemplateResolver();
//        springResourceTemplateResolver.setApplicationContext(applicationContext);
//        springResourceTemplateResolver.setPrefix("/WEB-INF/views/");
//        springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
//        springResourceTemplateResolver.setSuffix(".html");
//        springResourceTemplateResolver.setCharacterEncoding("UTF-8");
//        return springResourceTemplateResolver;
//    }
}
