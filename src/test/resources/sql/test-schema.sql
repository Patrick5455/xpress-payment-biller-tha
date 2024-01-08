CREATE TABLE IF NOT EXISTS user (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `created_at` datetime DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `first_name` varchar(255) DEFAULT NULL,
                        `is_active` BOOL DEFAULT NULL,
                        `last_name` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `updated_at` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
);