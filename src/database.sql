USE crm;

CREATE TABLE customers
(
id INT(11) AUTO_INCREMENT,
firstName VARCHAR(100),
lastName VARCHAR(100),
birthDate DATE,
email VARCHAR(50),
PRIMARY KEY (id)
);

CREATE TABLE employees
(
    id INT(11) AUTO_INCREMENT,
    firstName VARCHAR(100),
    lastName VARCHAR(100),
    zipCode VARCHAR(6),
    city VARCHAR(100),
    street VARCHAR(100),
    building VARCHAR(5),
    apartment VARCHAR(5),
    phone VARCHAR(20),
    notes TEXT,
    manHourCost decimal(10,2),
    avatarFileName varchar2(200),
    PRIMARY KEY (id)
);

CREATE TABLE vehicles
(
    id INT(11) AUTO_INCREMENT,
    model VARCHAR(100),
    manufacturer VARCHAR(100),
    productionYear INT(4),
    registrationNo VARCHAR(10),
    nextTechnicalCheckDate DATE,
    customerId INT,
    PRIMARY KEY (id),
    FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE
);

CREATE TABLE statuses
(
    id INT(11) AUTO_INCREMENT,
    statusName VARCHAR(30),
    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id INT(11) AUTO_INCREMENT,
    orderNumber VARCHAR(20),
    orderDate DATE,
    plannedRepairDate DATE,
    actualRepairStartDate DATETIME,
    actualRepairEndDate DATETIME,
    employeeId INT,
    problemDesc TEXT,
    repairDesc TEXT,
    statusId INT,
    vehicleId INT,
    customerCost decimal(10,2),
    partsCost decimal(10,2),
    employeeManHourCost decimal(10,2),
    manHoursSpent decimal(10,2),
    cancelReasonId INT,
    PRIMARY KEY (id),
    FOREIGN KEY (vehicleId) REFERENCES vehicles (id) ON DELETE CASCADE,
    FOREIGN KEY (statusId) REFERENCES statuses (id) ON DELETE CASCADE,
    FOREIGN KEY (employeeId) REFERENCES  employees (id) ON DELETE  CASCADE
);

CREATE TABLE ordercancelreasons
(
  id int(11) NOT NULL AUTO_INCREMENT,
  cancelReasonName varchar(50),
  PRIMARY KEY (id)
);

CREATE TABLE ordernumbers (
  id int(11) NOT NULL AUTO_INCREMENT,
  year int(4) NOT NULL,
  number int(11) NOT NULL,
  PRIMARY KEY (id)
);