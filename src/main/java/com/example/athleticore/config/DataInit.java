package com.example.athleticore.config;

import com.example.athleticore.dto.user.FullName;
import com.example.athleticore.entity.Booking;
import com.example.athleticore.entity.Schedule;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.*;
import com.example.athleticore.repository.BookingRepository;
import com.example.athleticore.repository.ScheduleRepository;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.repository.UserRepository;
import com.example.athleticore.service.impl.schedule.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final SessionRepository sessionRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<User> trainers = new ArrayList<>();
        List<User> clients = new ArrayList<>();

        List<UserSeed> seeds = List.of(
                new UserSeed("john.doe@example.com", "password123",
                        Role.TRAINER, "John", "Doe", "A.",
                        "+380501112233"),

                new UserSeed("jane.smith@example.com", "securepass",
                        Role.TRAINER, "Jane", "Smith", null,
                        "+380631234567"),

                new UserSeed("alex.kovalenko@example.com", "mypassword",
                        Role.CLIENT, "Oleksandr", "Kovalenko", "V.",
                        "+380981112233"),

                new UserSeed("olga.ivanova@example.com", "qwerty",
                        Role.CLIENT, "Olha", "Ivanova", null,
                        "+380931223344")
        );

        for (UserSeed s : seeds) {
            User user = new User();
            user.setEmail(s.email());
            user.setPassword(passwordEncoder.encode(s.password()));
            user.setRole(s.role());
            user.setFullName(new FullName(s.first(), s.last(), s.middle()));
            user.setPhoneNumber(s.phone());

            userRepository.save(user);

            if (user.getRole() == Role.TRAINER) {
                Schedule schedule = new Schedule();
                schedule.setTrainer(user);
                scheduleRepository.save(schedule);
                user.setSchedule(schedule);
                trainers.add(user);
            } else {
                clients.add(user);
            }
        }

        User user = new User();
        user.setEmail("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        User trainer1 = trainers.get(0);
        Schedule schedule1 = trainer1.getSchedule();

        List<Session> trainer1Sessions = List.of(
                s("Morning Cardio", "High-intensity cardio start of week.",
                        "GROUP", "2025-11-03T09:00", true, 60,
                        trainer1, Category.FITNESS, Difficulty.NORMAL, 20, schedule1),

                s("Boxing Basics", "Boxing fundamentals for beginners.",
                        "GROUP", "2025-11-03T18:00", true, 60,
                        trainer1, Category.FITNESS, Difficulty.HARD, 16, schedule1),

                s("CrossFit Power", "Full body crossfit workout.",
                        "GROUP", "2025-11-04T19:00", true, 90,
                        trainer1, Category.FITNESS, Difficulty.HARD, 15, schedule1),

                s("TRX Strength", "Suspension training.",
                        "GROUP", "2025-11-05T10:00", true, 60,
                        trainer1, Category.FITNESS, Difficulty.NORMAL, 14, schedule1),

                s("Strength Training", "Upper body strength.",
                        "INDIVIDUAL", "2025-11-05T17:00", false, 90,
                        trainer1, Category.FITNESS, Difficulty.HARD, 1, schedule1),

                s("Functional Workout", "Mobility training.",
                        "GROUP", "2025-11-06T08:00", true, 50,
                        trainer1, Category.FITNESS, Difficulty.LIGHT, 18, schedule1),

                s("Personal Coaching", "One-on-one coaching.",
                        "INDIVIDUAL", "2025-11-07T11:00", false, 90,
                        trainer1, Category.FITNESS, Difficulty.HARD, 1, schedule1),

                s("Stretch & Mobility", "Flexibility improvement.",
                        "GROUP", "2025-11-08T09:00", true, 45,
                        trainer1, Category.FITNESS, Difficulty.LIGHT, 20, schedule1),

                s("Sunday Yoga", "Light recovery yoga.",
                        "GROUP", "2025-11-09T10:00", true, 60,
                        trainer1, Category.YOGA, Difficulty.LIGHT, 20, schedule1)
        );

        sessionRepository.saveAll(trainer1Sessions);

        User trainer2 = trainers.get(1);
        Schedule schedule2 = trainer2.getSchedule();

        List<Session> trainer2Sessions = List.of(
                s("Morning Barre", "Barre fusion for tone.",
                        "GROUP", "2025-11-03T08:00", true, 55,
                        trainer2, Category.YOGA, Difficulty.NORMAL, 15, schedule2),

                s("Evening Yoga", "Slow flow yoga.",
                        "GROUP", "2025-11-03T19:00", true, 70,
                        trainer2, Category.YOGA, Difficulty.LIGHT, 18, schedule2),

                s("Pilates Classic", "Core strengthening pilates.",
                        "GROUP", "2025-11-04T10:00", true, 60,
                        trainer2, Category.PILATES, Difficulty.NORMAL, 12, schedule2),

                s("Relax Stretch", "Gentle stretching.",
                        "GROUP", "2025-11-05T18:00", true, 60,
                        trainer2, Category.YOGA, Difficulty.LIGHT, 18, schedule2),

                s("Meditation Practice", "Mindfulness training.",
                        "GROUP", "2025-11-06T08:30", true, 40,
                        trainer2, Category.YOGA, Difficulty.LIGHT, 25, schedule2),

                s("Soft Yoga Flow", "Calm movements.",
                        "GROUP", "2025-11-06T17:30", true, 60,
                        trainer2, Category.YOGA, Difficulty.LIGHT, 20, schedule2),

                s("Pilates Core", "Deep core activation.",
                        "GROUP", "2025-11-07T09:00", false, 60,
                        trainer2, Category.PILATES, Difficulty.NORMAL, 14, schedule2),

                s("Weekend Yoga Flow", "Medium intensity yoga.",
                        "GROUP", "2025-11-08T11:00", true, 75,
                        trainer2, Category.YOGA, Difficulty.NORMAL, 20, schedule2),

                s("Restorative Yoga", "Full relaxation.",
                        "GROUP", "2025-11-09T09:00", true, 60,
                        trainer2, Category.YOGA, Difficulty.LIGHT, 16, schedule2)
        );

        sessionRepository.saveAll(trainer2Sessions);

        User c1 = clients.get(0); // id=3
        User c2 = clients.get(1); // id=4

        bookingRepository.save(new Booking(null, c1, trainer1Sessions.get(0), BookingStatus.CONFIRMED));
        bookingRepository.save(new Booking(null, c2, trainer1Sessions.get(1), BookingStatus.CONFIRMED));
        bookingRepository.save(new Booking(null, c1, trainer1Sessions.get(2), BookingStatus.CONFIRMED));
        bookingRepository.save(new Booking(null, c2, trainer1Sessions.get(7), BookingStatus.CONFIRMED));
        bookingRepository.save(new Booking(null, c1, trainer1Sessions.get(8), BookingStatus.CONFIRMED));
    }

    private Session s(
            String name, String desc, String type, String date,
            boolean repeat, int duration, User trainer,
            Category cat, Difficulty diff, int max, Schedule schedule
    ) {
        return Session.builder()
                .name(name)
                .description(desc)
                .sessionType(SessionType.valueOf(type))
                .date(LocalDateTime.parse(date))
                .isRepeat(repeat)
                .duration(duration)
                .trainer(trainer)
                .category(cat)
                .difficulty(diff)
                .maxParticipants(max)
                .schedule(schedule)
                .build();
    }

    private record UserSeed(
            String email, String password, Role role,
            String first, String last, String middle,
            String phone
    ) {}
}


