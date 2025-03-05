DROP DATABASE IF EXISTS MS2Demployees;
CREATE DATABASE MS2Demployees;
use MS2Demployees;
DROP TABLE IF EXISTS employees;
 
CREATE TABLE employees (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  mail VARCHAR(100) NOT NULL,
  password VARCHAR(250) NOT NULL
);
 
INSERT INTO employees (first_name, last_name, mail, password) VALUES
  ('Laurent', 'GINA', 'laurentgina@mail.com', 'laurent'),
  ('Sophie', 'FONCEK', 'sophiefoncek@mail.com', 'sophie'),
  ('Agathe', 'FEELING', 'agathefeeling@mail.com', 'agathe');