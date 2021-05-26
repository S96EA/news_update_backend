package com.news.update.component;

import com.news.update.entity.*;
import com.news.update.repository.*;
import com.news.update.service.SocialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdminsRepository adminsRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private SocialServiceImpl socialService;

    @Value("${spring.datasource.initialization-mode}")
    private String init;

    @Override
    public void run(String... args) throws Exception {
        if (init.equals("always")) {
            try {
                Role roleAdmin = new Role();
                roleAdmin.setName("ROLE_ADMIN");
                Admins admins = new Admins();
                admins.setFullname("Orifjon Yunusjonov");
                admins.setUsername("admin");
                admins.setPassword(
                        passwordEncoder.encode(
                                "root"
                        )
                );
                admins.setPhone("+998932099924");
                admins.setSocial("t.me/yunusjanoff");
                roleRepository.save(roleAdmin);
                Set<Role> roles = new HashSet<>(roleRepository.findAll());
                admins.setRoles(roles);
                adminsRepository.save(admins);
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                Category category = new Category();
                String array[] = new String[]{"Jamiyat", "Iqtisod", "Dunyo", "Biznes", "Ijtimoiy", "Sayohat", "Salomatlik", "Moda", "Avto", "Sport", "Texnologiya", "Internet"};
                String array1[] = new String[]{"Общество", "Экономика", "Мир", "Бизнес", "Социальное", "Путешествовать", "Здоровье", "Мода", "Авто", "Спорт", "Технологии", "Интернет"};

                for (int i = 0; i < array.length; i++) {
                    category = new Category();
                    category.setNameUz(array[i]);
                    category.setNameRu(array1[i]);
                    categoryRepository.save(category);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                Tags tags = new Tags();
                String array2[] = new String[]{"Dizayn", "Iqtisod", "Dunyo", "Biznes", "Ijtimoiy", "Sayohat", "Salomatlik", "Moda", "Avto", "Sport", "Texnologiya", "Internet",
                        "Fotografiya", "Qiziqarli"
                };
                String array3[] = new String[]{"Дизайн", "Экономика", "Мир", "Бизнес", "Социальное", "Путешествовать", "Здоровье", "Мода", "Авто", "Спорт", "Технологии", "Интернет",
                        "Фотография", "Интересно"
                };
                Social social = new Social();
                social.setYoutube(1);
                social.setFacebook(1);
                social.setTelegram(1);
                social.setInstagram(1);
                social.setTwitter(1);

                socialService.create(social);
                for (int i = 0; i < array2.length; i++) {
                    tags = new Tags();
                    tags.setTagUz(array2[i]);
                    tags.setTagRu(array3[i]);
                    tagsRepository.save(tags);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
