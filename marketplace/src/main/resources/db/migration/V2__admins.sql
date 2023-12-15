CREATE TABLE admin
(
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

INSERT INTO admin (username, password)
VALUES ('admin', '$2a$10$EBtdo8yT2Lp6P6IBLWbh0ub900n62pFut6PAueM34Rm49FZD24qC.');
