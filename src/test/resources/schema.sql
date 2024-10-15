CREATE TABLE id_generator_table (
                                    sequence_name VARCHAR(255) NOT NULL,
                                    next_val BIGINT NOT NULL,
                                    PRIMARY KEY (sequence_name)
);