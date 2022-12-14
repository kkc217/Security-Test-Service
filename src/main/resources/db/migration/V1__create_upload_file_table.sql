CREATE TABLE upload_file  (
                         file_id       bigint         NOT NULL AUTO_INCREMENT primary key,
                         name          varchar(200)   NOT NULL,
                         path          varchar(200)   NOT NULL,
                         content_type  varchar(100),
                         size          bigint
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci