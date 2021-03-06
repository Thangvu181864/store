USE master

GO

IF EXISTS (
  SELECT name
FROM sys.databases
WHERE name = N'Store'
)
DROP DATABASE Store

GO

CREATE DATABASE Store

GO

USE Store

GO

CREATE TABLE Roles
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    "name" NVARCHAR(20) NOT NULL UNIQUE,
    note NVARCHAR(100)
)

GO

CREATE TABLE Accounts
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    username VARCHAR(32) NOT NULL UNIQUE,
    "password" NVARCHAR(200) NOT NULL,
    roleId INT NOT NULL FOREIGN KEY (roleId) REFERENCES Roles(id),
    note NVARCHAR(100),
    destroy BIT NOT NULL DEFAULT 0,
)

GO

CREATE TABLE Employees
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    fullname NVARCHAR(50) NOT NULL,
    birthday DATE NOT NULL,
    gender INT NOT NULL,
    "address" NVARCHAR(50) NOT NULL,
    phone VARCHAR(10) NOT NULL UNIQUE,
    accountId INT NOT NULL FOREIGN KEY (accountId) REFERENCES Accounts(id),
    destroy BIT NOT NULL DEFAULT 0,
)

GO

CREATE TABLE Vendors
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    "name" NVARCHAR(50) NOT NULL,
    "address" NVARCHAR(50) NOT NULL,
    email NVARCHAR(50) UNIQUE,
    phone VARCHAR(10) UNIQUE,
    note NVARCHAR(100),
    destroy BIT NOT NULL DEFAULT 0,
)

GO

CREATE TABLE Products
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    "name" NVARCHAR(50) NOT NULL,
    priceBuy MONEY NOT NULL,
    priceSell MONEY NOT NULL,
    dateManufacture DATE NOT NULL,
    dateExpiration DATE NOT NULL,
    vendorId INT NOT NULL FOREIGN KEY (vendorId) REFERENCES Vendors(id),
    quantity INT NOT NULL DEFAULT 0,
    "image" VARCHAR(100) NOT NULL,
    note NVARCHAR(100),
    destroy BIT NOT NULL DEFAULT 0,
)

GO

CREATE TABLE Invoices
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    employeeId INT NOT NULL FOREIGN KEY (employeeId) REFERENCES Employees(id),
    typeInvoice CHAR(6) NOT NULL CHECK(typeInvoice IN('EXPORT','IMPORT')),
    createAt DATE DEFAULT GETDATE(),
    total MONEY DEFAULT 0,
    note NVARCHAR(100),
    destroy BIT NOT NULL DEFAULT 0,
)

GO

CREATE TABLE DetailInvoices
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    invoiceId INT NOT NULL FOREIGN KEY (invoiceId) REFERENCES Invoices(id),
    productId INT NOT NULL FOREIGN KEY (productId) REFERENCES Products(id),
    priceSell MONEY NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    total MONEY NOT NULL DEFAULT 0,
    note NVARCHAR(100),
    destroy BIT NOT NULL DEFAULT 0,
)

GO

CREATE TRIGGER tg_Employee_Update
ON Employees
FOR UPDATE
AS
BEGIN
    IF UPDATE(destroy)
    BEGIN
        DECLARE @accountId INT
        SELECT @accountId = accountId
        FROM deleted
        UPDATE Accounts SET destroy = 1 WHERE id = @accountId
    END
END

GO

CREATE TRIGGER tg_Invoice_Update
ON Invoices
FOR UPDATE
AS
BEGIN
    IF UPDATE(destroy)
    BEGIN
        DECLARE @invoiceId INT
        SELECT @invoiceId = id
        FROM deleted
        UPDATE DetailInvoices SET destroy = 1 WHERE id = @invoiceId
    END
END

GO

CREATE TRIGGER tg_DetailInvoices_Insert
ON DetailInvoices
FOR INSERT
AS 
BEGIN
    IF  (SELECT (Products.quantity - inserted.quantity) AS quantity
    FROM Products INNER JOIN inserted ON Products.id = inserted.productId) < 0
    BEGIN
        RAISERROR  ('Quantity is not enough',12,1)
        ROLLBACK TRAN
    END
    ELSE
    BEGIN
        IF (SELECT Invoices.typeInvoice AS typeInvoice
        FROM inserted INNER JOIN Invoices ON inserted.invoiceId = Invoices.id) = N'EXPORT'
        BEGIN
            UPDATE Products SET Products.quantity = Products.quantity - inserted.quantity FROM Products INNER JOIN inserted ON Products.id = inserted.productId
        END
        ELSE
        BEGIN
            UPDATE Products SET Products.quantity = Products.quantity + inserted.quantity FROM Products INNER JOIN inserted ON Products.id = inserted.productId
        END
        UPDATE Invoices SET Invoices.total = Invoices.total + inserted.total FROM Invoices JOIN inserted ON Invoices.id = inserted.invoiceId
    END
END

GO


INSERT Roles
    ("name")
VALUES('Ban Hang')
INSERT Roles
    ("name")
VALUES('Quan ly')


INSERT Accounts
    (username,"password",roleId)
VALUES('Thangvu', '$2a$10$q/1quxeh8vdjDqCJB1a8re17Kbzhr0oYci7tQbJ4nsjMLqpqIqFxa', 2)
INSERT Accounts
    (username,"password",roleId)
VALUES('Huyhoang', '$2a$10$q/1quxeh8vdjDqCJB1a8re17Kbzhr0oYci7tQbJ4nsjMLqpqIqFxa', 1)


INSERT Employees
    (fullname,birthday,gender,"address",phone,accountId)
VALUES( N'V?? V??n Th???ng', '04/14/2001', 1, N'H?? N???i', '0972068418', 1)

INSERT Employees
    (fullname,birthday,gender,"address",phone,accountId)
VALUES( N'Nguy???n Huy Ho??ng', '01/01/2001', 1, N'H?? N???i', '0987654321', 2)

INSERT Vendors
    ("name" ,"address" ,email , phone)
VALUES( N'Vin Group', N'H?? N???i', 'vingroup@gmail.com', '0987612345')
INSERT Vendors
    ("name" ,"address" ,email , phone)
VALUES(N'C??NG TY TNHH MTV', N'???? N???ng', 'mtv@gmail.com', '0123498765')

INSERT Products
    ("name" ,priceBuy,priceSell, dateManufacture, dateExpiration,vendorId ,quantity ,"image")
VALUES(N'B??nh M??', 10000, 15000, '01/01/2001', '01/01/2002', 1, 50, 'https://cdn.tgdd.vn/2020/09/CookProduct/1260-1200x676-52.jpg')

INSERT Products
    ("name" ,priceBuy,priceSell, dateManufacture, dateExpiration,vendorId ,quantity ,"image")
VALUES(N'N?????c l???c', 65000, 66000, '01/01/2004', '01/01/2005', 2, 40, 'https://giaonuoc.vn/wp-content/uploads/2018/03/nuoc-lavie-19-lit-2.jpg')

INSERT Products
    ("name" ,priceBuy,priceSell, dateManufacture, dateExpiration,vendorId ,quantity ,"image")
VALUES(N'B??nh quy', 20000, 22000, '01/01/2007', '01/01/2008', 1, 60, 'https://minhcaumart.vn/media/com_eshop/products/7622210607300.jpg')

INSERT Invoices
    (employeeId ,typeInvoice ,note )
VALUES( 1, 'EXPORT', N'Nhan Vien Ban Hang')

INSERT Invoices
    (employeeId ,typeInvoice ,note)
VALUES( 1, 'EXPORT', N'Nhan Vien Ban Hang')

INSERT Invoices
    (employeeId ,typeInvoice ,note)
VALUES( 2, 'IMPORT', N'Nhan Vien Quan Ly')


INSERT DetailInvoices
    (invoiceId ,productId , priceSell, quantity ,total ,note )
VALUES(1, 1, (SELECT priceSell
        FROM Products
        WHERE id = 1), 6, (SELECT priceSell
        FROM Products
        WHERE id = 1) * 6, N'Hoa don 01')

INSERT DetailInvoices
    (invoiceId ,productId ,priceSell,quantity ,total ,note )
VALUES( 1, 3, (SELECT priceSell
        FROM Products
        WHERE id = 3), 2, (SELECT priceSell
        FROM Products
        WHERE id = 3) * 2, N'Hoa don 01')

INSERT DetailInvoices
    (invoiceId ,productId ,priceSell,quantity ,total ,note )
VALUES( 2, 2, (SELECT priceSell
        FROM Products
        WHERE id = 2), 2, (SELECT priceSell
        FROM Products
        WHERE id = 2) * 2, N'Hoa don 02')

INSERT DetailInvoices
    (invoiceId ,productId ,priceSell,quantity ,total ,note )
VALUES( 2, 1, (SELECT priceSell
        FROM Products
        WHERE id = 1) , 1, (SELECT priceSell
        FROM Products
        WHERE id = 1) * 1, N'Hoa don 02')

INSERT DetailInvoices
    (invoiceId ,productId ,priceSell,quantity ,total ,note )
VALUES( 3, 1, (SELECT priceSell
        FROM Products
        WHERE id = 1), 17, (SELECT priceSell
        FROM Products
        WHERE id = 1) * 17, N'Hoa don 03')

INSERT DetailInvoices
    (invoiceId ,productId ,priceSell,quantity ,total ,note )
VALUES( 3, 2, (SELECT priceSell
        FROM Products
        WHERE id = 2), 2, (SELECT priceSell
        FROM Products
        WHERE id = 2) * 2, N'Hoa don 03')

INSERT DetailInvoices
    (invoiceId ,productId ,priceSell,quantity ,total ,note )
VALUES( 3, 3,
        (SELECT priceSell
        FROM Products
        WHERE id = 3), 20,
        (SELECT priceSell
        FROM Products
        WHERE id = 3) * 20, N'Hoa don 03')

SELECT *
FROM Accounts

SELECT *
FROM Employees

SELECT *
FROM Vendors

SELECT *
FROM Products

SELECT *
FROM Invoices

SELECT *
FROM DetailInvoices

