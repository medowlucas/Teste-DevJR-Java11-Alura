ALTER TABLE Videos
    ADD section_id BIGINT REFERENCES Section(id);

ALTER TABLE Section
    ADD video_id BIGINT REFERENCES Videos(id);
