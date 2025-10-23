CREATE TABLE client_notifications
(
    client_id       BIGINT NOT NULL,
    notification_id BIGINT NOT NULL,
    PRIMARY KEY (client_id, notification_id),
    FOREIGN KEY (client_id) REFERENCES clients (id),
    FOREIGN KEY (notification_id) REFERENCES notifications (id)
);