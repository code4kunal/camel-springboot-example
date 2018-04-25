CREATE TABLE vehicles(
      id bigserial  PRIMARY KEY,
      unique_name varchar(100) NOT NULL UNIQUE,
      company varchar(50) NOT NULL,
      mileage NUMERIC ,
      price NUMERIC ,
      category varchar(50) DEFAULT NULL,
      year_of_manufacturing INTEGER DEFAULT NULL

  )