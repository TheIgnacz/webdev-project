package webdevproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class WebdevProjectApplication {
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        SpringApplication.run(WebdevProjectApplication.class, args);
    }

}
