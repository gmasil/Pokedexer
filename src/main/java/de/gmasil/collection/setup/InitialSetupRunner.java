package de.gmasil.collection.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.gmasil.collection.card.Card;
import de.gmasil.collection.card.CardRepository;
import de.gmasil.collection.security.User;
import de.gmasil.collection.security.UserRepository;
import de.gmasil.collection.security.UserService;
import de.gmasil.collection.series.Series;
import de.gmasil.collection.series.SeriesRepository;

@Component
public class InitialSetupRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${initial.user.name:#{null}}")
    private String username;

    @Value("${initial.user.password:#{null}}")
    private String password;

    @EventListener(ApplicationReadyEvent.class)
    public void setupInitialUser() {
        if (userRepository.count() == 0 && username != null && password != null) {
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            userService.encodePassword(user);
            userService.save(user);
        }
        createTestData();
    }

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private SeriesRepository seriesRepo;

    public void createTestData() {
        Series initialSeries = null;
        if (seriesRepo.count() == 0) {
            Series series = new Series();
            series.setName("1998 P.M. Japanese Gym");
            initialSeries = seriesRepo.save(series);
            series = new Series();
            series.setName("2020 Pokemon SWSH");
            seriesRepo.save(series);
        }
        if (cardRepo.count() == 0) {
            Card card = new Card();
            card.setName("Misty's Tears");
            cardRepo.save(card);
            card = new Card();
            card.setName("Misty's Gyarados");
            card.setCardNumber(130);
            card.setSeries(initialSeries);
            cardRepo.save(card);
        }
    }
}
