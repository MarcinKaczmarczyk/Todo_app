drop table if exists task_events;
create table task_events
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    task_id    int,
    occurrence datetime,
    name       varchar(30)

);