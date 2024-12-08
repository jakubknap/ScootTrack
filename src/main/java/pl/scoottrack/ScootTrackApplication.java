package pl.scoottrack;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.scoottrack.role.model.Role;
import pl.scoottrack.role.repository.RoleRepository;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableAsync
public class ScootTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScootTrackApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER")
                              .isEmpty()) {
                roleRepository.save(Role.builder()
                                        .name("USER")
                                        .build());
            }

            if (roleRepository.findByName("ADMIN")
                              .isEmpty()) {
                roleRepository.save(Role.builder()
                                        .name("ADMIN")
                                        .build());
            }
        };
    }
}