package cat.itacademy.s05.t02.VirtualPet.config;

import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.model.enums.Role;
import cat.itacademy.s05.t02.VirtualPet.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica si l'usuari ADMIN ja existeix
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                // Crea l'usuari ADMIN si no existeix
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("adminpassword2")); // Encripta la contrasenya
                adminUser.setRole(Role.ROLE_ADMIN); // Assigna el rol ADMIN
                userRepository.save(adminUser);
                //System.out.println("Usuari ADMIN creat amb èxit.");
            } /*else {
                System.out.println("L'usuari ADMIN ja existeix.");
            }*/
        };
    }

    //una altra opció
    /*@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
@Override
    public void run(String... args) {}*/
}
