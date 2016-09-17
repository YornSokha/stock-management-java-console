CREATE TABLE tblstock(
   stoid serial PRIMARY KEY     NOT NULL,
   name           varchar (50)    NOT NULL,
   unitprice      double precision     NOT NULL,
   sqty        int Not Null,
   impdate         varchar (20)
);