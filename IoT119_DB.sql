-- 1. DB 만들기
CREATE DATABASE IoT119;

-- 2. table 만들기
CREATE TABLE IF NOT EXISTS Customer (
  customer_id SERIAL PRIMARY KEY,
  customer_name varchar(20) NOT NULL,
  customer_category varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Plan (
  plan_id SERIAL PRIMARY KEY,
  plan_name varchar(20) NOT NULL,
  plan_data varchar(20) NOT NULL,
  plan_charge Integer NOT NULL
);

CREATE TABLE IF NOT EXISTS Usage (
  usage_id SERIAL PRIMARY KEY,
  customer_id Integer NOT NULL REFERENCES Customer(customer_id),
  usage_date date NOT NULL,
  usage_per_day Integer NOT NULL
);

CREATE TABLE IF NOT EXISTS Recommandation (
  recommandation_id SERIAL PRIMARY KEY,
  customer_id Integer NOT NULL REFERENCES Customer(customer_id),
  plan_id Integer NOT NULL REFERENCES Plan(plan_id),
  recommandation_date timestamp NOT NULL DEFAULT now()
);

-- 2.1. 생성확인
SELECT * FROM Customer;
SELECT * FROM Plan;
SELECT * FROM Usage;
SELECT * FROM Recommandation;

-- 2.2 테이블 삭제
DROP TABLE IF EXISTS Recommandation;
DROP TABLE IF EXISTS Usage;
DROP TABLE IF EXISTS Plan;
DROP TABLE IF EXISTS Customer;

-- 3. 데이터 추가
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT38', '8 MB', 3850);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT55', '50 MB', 5500);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT77', '140 MB', 7700);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT110', '1 GB', 11000);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT165', '2 GB', 16500);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT220', '3 GB', 22000);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT247', '3.5 GB', 24750);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT275', '4 GB', 27500);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT302', '5 GB', 30250);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT330', '6 GB', 33000);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT550', '10 GB', 55000);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT880', '20 GB', 88000);
INSERT INTO Plan (plan_name, plan_data, plan_charge) VALUES('IoT1045', '30 GB', 104500);

-- 4. 서버로 보낼 데이터
SELECT u.customer_id, c.customer_category, u.usage_date, u.usage_per_day
  FROM Usage AS u
  INNER JOIN Customer AS c ON u.customer_id = c.customer_id
  WHERE u.customer_id = (
    SELECT MAX(customer_id) FROM Customer
  );

