-- ===== USERS =====
INSERT INTO USERS (EMAIL, PASSWORD, ROLE, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, CATEGORY)
VALUES
--     pass = password123
    ('john.doe@example.com', '$2a$10$JhrCCu7pzLoKz8.pRFdzIOrr.NJsd7J6I/fNTv4tcE.TqElZPZ8S6', 'TRAINER', 'John', 'Doe', 'A.', '+380501112233', 'FITNESS'),
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
INSERT INTO SESSIONS
(NAME, DESCRIPTION, SESSION_TYPE, DATE, IS_REPEAT, DURATION, USER_ID, CATEGORY, DIFFICULTY, MAX_PARTICIPANTS, SCHEDULE_ID)
VALUES
('Morning Cardio', 'High-intensity cardio start of week.', 'GROUP', '2025-11-03 09:00:00', true, 60, 1, 'FITNESS', 'NORMAL', 20, 1),
('Boxing Basics', 'Boxing fundamentals for beginners.', 'GROUP', '2025-11-03 18:00:00', true, 60, 1, 'FITNESS', 'HARD', 16, 1),

('CrossFit Power', 'Full body crossfit workout.', 'GROUP', '2025-11-04 19:00:00', true, 90, 1, 'FITNESS', 'HARD', 15, 1),

('TRX Strength', 'Suspension training for full body.', 'GROUP', '2025-11-05 10:00:00', true, 60, 1, 'FITNESS', 'NORMAL', 14, 1),
('Strength Training', 'Upper body strength focus.', 'INDIVIDUAL', '2025-11-05 17:00:00', false, 90, 1, 'FITNESS', 'HARD', 1, 1),

('Functional Workout', 'Mobility + functional movements.', 'GROUP', '2025-11-06 08:00:00', true, 50, 1, 'FITNESS', 'LIGHT', 18, 1),

('Personal Coaching', 'One-on-one personalized training.', 'INDIVIDUAL', '2025-11-07 11:00:00', false, 90, 1, 'FITNESS', 'HARD', 1, 1),

('Stretch & Mobility', 'Gentle stretching and flexibility.', 'GROUP', '2025-11-08 09:00:00', true, 45, 1, 'FITNESS', 'LIGHT', 20, 1),

('Sunday Yoga', 'Light yoga session for recovery.', 'GROUP', '2025-11-09 10:00:00', true, 60, 1, 'YOGA', 'LIGHT', 20, 1);

INSERT INTO SESSIONS
(NAME, DESCRIPTION, SESSION_TYPE, DATE, IS_REPEAT, DURATION, USER_ID, CATEGORY, DIFFICULTY, MAX_PARTICIPANTS, SCHEDULE_ID)
VALUES
('Morning Barre', 'Barre fusion for posture & tone.', 'GROUP', '2025-11-03 08:00:00', true, 55, 2, 'YOGA', 'NORMAL', 15, 2),
('Evening Yoga', 'Slow flow yoga for all levels.', 'GROUP', '2025-11-03 19:00:00', true, 70, 2, 'YOGA', 'LIGHT', 18, 2),

('Pilates Classic', 'Core strengthening pilates practice.', 'GROUP', '2025-11-04 10:00:00', true, 60, 2, 'PILATES', 'NORMAL', 12, 2),

('Relax Stretch', 'Gentle stretching session.', 'GROUP', '2025-11-05 18:00:00', true, 60, 2, 'YOGA', 'LIGHT', 18, 2),

('Meditation Practice', 'Breathing & mindfulness.', 'GROUP', '2025-11-06 08:30:00', true, 40, 2, 'YOGA', 'LIGHT', 25, 2),
('Soft Yoga Flow', 'Calm & slow movements for mind-body.', 'GROUP', '2025-11-06 17:30:00', true, 60, 2, 'YOGA', 'LIGHT', 20, 2),

('Pilates Core', 'Deep core activation exercises.', 'GROUP', '2025-11-07 09:00:00', false, 60, 2, 'PILATES', 'NORMAL', 14, 2),

('Weekend Yoga Flow', 'Medium intensity weekend practice.', 'GROUP', '2025-11-08 11:00:00', true, 75, 2, 'YOGA', 'NORMAL', 20, 2),

('Restorative Yoga', 'Full relaxation and deep breathing.', 'GROUP', '2025-11-09 09:00:00', true, 60, 2, 'YOGA', 'LIGHT', 16, 2);

