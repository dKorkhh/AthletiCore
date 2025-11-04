-- ===== USERS =====
INSERT INTO USERS (EMAIL, PASSWORD, ROLE, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, CATEGORY)
VALUES
    ('john.doe@example.com', 'password123', 'TRAINER', 'John', 'Doe', 'A.', '+380501112233', 'FITNESS'),
    ('jane.smith@example.com', 'securepass', 'TRAINER', 'Jane', 'Smith', NULL, '+380631234567', 'YOGA'),
    ('alex.kovalenko@example.com', 'mypassword', 'CLIENT', 'Oleksandr', 'Kovalenko', 'V.', '+380981112233', 'PILATES'),
    ('olga.ivanova@example.com', 'qwerty', 'CLIENT', 'Olha', 'Ivanova', NULL, '+380931223344', 'PILATES');

-- ===== SCHEDULES =====
INSERT INTO SCHEDULES (USER_ID)
VALUES
    (1),
    (2),
    (3),
    (4);

-- ===== SESSIONS =====
INSERT INTO SESSIONS (NAME, DESCRIPTION, SESSION_TYPE, DATE, DURATION, USER_ID, CATEGORY, DIFFICULTY, MAX_PARTICIPANTS, SCHEDULE_ID)
VALUES
    ('Morning Cardio', 'Intense morning cardio session to boost energy.', 'GROUP', '2025-11-05', 60, 1, 'FITNESS', 'NORMAL', 15, 1),
    ('Yoga for Beginners', 'Relaxing yoga class for beginners focusing on flexibility.', 'GROUP', '2025-11-06', 75, 2, 'YOGA', 'LIGHT', 12, 2),
    ('Personal Training', 'One-on-one strength training with John Doe.', 'INDIVIDUAL', '2025-11-07', 90, 1, 'FITNESS', 'HARD', 1, 1),
    ('Stretch & Relax', 'Gentle stretching and breathing exercises.', 'GROUP', '2025-11-08', 45, 2, 'BOXING', 'LIGHT', 10, 2),
    ('Pilates Core', 'Focus on core stability and posture.', 'GROUP', '2025-11-09', 60, 2, 'PILATES', 'NORMAL', 10, 2);
