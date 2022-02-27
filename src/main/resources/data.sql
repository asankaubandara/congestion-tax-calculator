CREATE TABLE cities ( 
   id INT PRIMARY KEY, 
   name VARCHAR(50) NOT NULL, 
   code VARCHAR(20) NOT NULL, 
   submission_date DATE 
);

INSERT INTO cities (id, name, code) VALUES (1, 'Stockholm', 'ST');
INSERT INTO cities (id, name, code) VALUES (2, 'Gothenburg', 'GB');
INSERT INTO cities (id, name, code) VALUES (3, 'Malmo', 'ML');
INSERT INTO cities (id, name, code) VALUES (4, 'Uppsala', 'UP');


CREATE TABLE holiday(
  id INT PRIMARY KEY,
  date TIMESTAMP NOT NULL,
  city_id INT,
  foreign key (city_id) references cities(id)
)

INSERT INTO holiday (id, date, city_id) VALUES('1', parsedatetime('01-01-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('2', parsedatetime('28-03-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('3', parsedatetime('29-03-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('4', parsedatetime('01-04-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('5', parsedatetime('30-04-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('6', parsedatetime('01-05-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('7', parsedatetime('08-05-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('8', parsedatetime('09-05-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('9', parsedatetime('06-06-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('10', parsedatetime('21-06-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('11', parsedatetime('01-11-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('12', parsedatetime('24-12-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('13', parsedatetime('25-12-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('14', parsedatetime('26-12-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
INSERT INTO holiday (id, date, city_id) VALUES('15', parsedatetime('31-12-2013 00:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);


CREATE TABLE rate(
  id INT PRIMARY KEY,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP NOT NULL,
  tax INT NOT NULL,
  city_id INT,
  foreign key (city_id) references cities(id)
)

INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('1', parsedatetime('01-01-1970 06:00', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 06:29', 'dd-MM-yyyy hh:mm'), 2, 8);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('2', parsedatetime('01-01-1970 06:30', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 06:59', 'dd-MM-yyyy hh:mm'), 2, 13);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('3', parsedatetime('01-01-1970 07:00', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 07:59', 'dd-MM-yyyy hh:mm'), 2, 18);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('4', parsedatetime('01-01-1970 08:00', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 08:29', 'dd-MM-yyyy hh:mm'), 2, 13);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('5', parsedatetime('01-01-1970 08:30', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 14:59', 'dd-MM-yyyy hh:mm'), 2, 8);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('6', parsedatetime('01-01-1970 15:00', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 15:29', 'dd-MM-yyyy hh:mm'), 2, 13);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('7', parsedatetime('01-01-1970 15:30', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 16:59', 'dd-MM-yyyy hh:mm'), 2, 18);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('8', parsedatetime('01-01-1970 17:00', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 17:59', 'dd-MM-yyyy hh:mm'), 2, 13);
INSERT INTO rate (id, start_date, end_date, city_id, tax) VALUES('9', parsedatetime('01-01-1970 18:00', 'dd-MM-yyyy hh:mm'), parsedatetime('01-01-1970 18:29', 'dd-MM-yyyy hh:mm'), 2, 8);