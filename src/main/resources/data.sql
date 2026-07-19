
INSERT INTO account (id, username, password, role) VALUES
    (1, 'sgodzheva', 'somehash', 'INSTRUCTOR');
ALTER TABLE account ALTER COLUMN id RESTART WITH 2;

INSERT INTO instructor (id, account_id, first_name, last_name, email, phone_number) VALUES
    (1, 1, 'Svetlozara', 'Godzheva', 'svetlozara.godzheva@email.com', '416-555-0100');
ALTER TABLE instructor ALTER COLUMN id RESTART WITH 2;

INSERT INTO course (id, instructor_id, code, name, description, start_date, end_date) VALUES
    (1, 1, 'CPAN-228', 'Web Application Development', 'Server-side web development with Spring Boot and Thymeleaf.', '2026-01-10 09:00:00', '2026-04-24 17:00:00'),
    (2, 1, 'CPAN-214', 'Database Systems', 'Relational database design, SQL, and normalization.', '2026-03-05 09:00:00', '2026-04-24 17:00:00'),
    (3, 1, 'CPAN-212', 'Object-Oriented Programming', 'Core OOP principles using Java.', '2026-01-14 09:00:00', '2026-04-24 17:00:00'),
    (4, 1, 'CPAN-224', 'Data Structures and Algorithms', 'Fundamental data structures and algorithmic problem solving.', '2026-01-20 09:00:00', '2026-04-24 17:00:00'),
    (5, 1, 'CPAN-243', 'Software Engineering', 'Software development lifecycle, testing, and version control.', '2026-05-06 09:00:00', '2026-04-24 17:00:00');
ALTER TABLE course ALTER COLUMN id RESTART WITH 6;

INSERT INTO assignment (id, course_id, name, description, due_date) VALUES
    (1, 1, 'CPAN-228 - Assignment 1', 'Create a small blog website', '2026-06-27 23:59:00'),
    (2, 1, 'CPAN-214 - Assignment 2', 'Design a database', '2026-07-02 23:59:00'),
    (3, 1, 'CPAN-224 - Assignment 3', 'Implement bubble sort', '2026-07-12 23:59:00'),
    (4, 1, 'CPAN-243 - Assignment 4', 'Create a test plan for an e-commerce system', '2026-08-22 23:59:00');
ALTER TABLE assignment ALTER COLUMN id RESTART WITH 5;
