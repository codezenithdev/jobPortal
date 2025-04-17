CREATE TABLE IF NOT EXISTS user_type (
    user_type_id INT PRIMARY KEY,
    user_type_name VARCHAR(255)
);

INSERT INTO user_type (user_type_id, user_type_name) VALUES
(1, 'Recruiter'),
(2, 'Job Seeker')
ON DUPLICATE KEY UPDATE user_type_name=VALUES(user_type_name);
