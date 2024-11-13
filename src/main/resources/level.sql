/*
-- Query: SELECT * FROM finut.level
LIMIT 0, 5000

-- Date: 2024-11-06 01:59
*/
Use finut;

INSERT INTO level (`id`, `level_name`, `salary`, `level_quiz_cnt`) VALUES (1, 'PARTTIMEJOB', 400000, 5);
INSERT INTO level (`id`, `level_name`, `salary`, `level_quiz_cnt`) VALUES (2, 'INTERN', 1000000, 5);
INSERT INTO level (`id`, `level_name`, `salary`, `level_quiz_cnt`) VALUES (3, 'STAFF', 2000000, 5);
INSERT INTO level (`id`, `level_name`, `salary`, `level_quiz_cnt`) VALUES (4, 'ASSOCIATEMANAGER', 4000000, 5);
INSERT INTO level (`id`, `level_name`, `salary`, `level_quiz_cnt`) VALUES (5, 'MANAGER', 6000000, 5);
INSERT INTO level (`id`, `level_name`, `salary`, `level_quiz_cnt`) VALUES (6, 'SENIORMANAGER', 8000000, 5);
INSERT INTO level (`id`, `level_name`, `salary`, `level_quiz_cnt`) VALUES (7, 'DIRECTOR', 10000000, 5);

select * from level;
