CREATE TABLE Enroll (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES Course(id),
    user_id BIGINT NOT NULL REFERENCES User(id),
    enroll_date DATE NOT NULL
);