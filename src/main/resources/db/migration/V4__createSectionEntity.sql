CREATE TABLE Section (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(30) NOT NULL,
    title VARCHAR(40) NOT NULL,
    author_username VARCHAR(30) NOT NULL,
    course BIGINT REFERENCES Course(id)
);
 