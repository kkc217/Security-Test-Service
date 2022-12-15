CREATE TABLE history  (
                         id           bigint         NOT NULL AUTO_INCREMENT primary key,
                         file_id      bigint         NOT NULL,
                         result       varchar(100),
                         create_time  timestamp,
                         FOREIGN KEY (file_id) REFERENCES upload_file(file_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci