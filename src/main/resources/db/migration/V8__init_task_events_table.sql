drop TABLE IF EXISTS task_events;
CREATE TABLE task_events(
    id INT primary key auto_increment,
    task_id int,
    occurence datetime,
    name varchar(30)
)