use [Nutrition-App]
go

create table Device (
ID int  PRIMARY KEY identity(0,1),
times_used int,
times_correct int
);

create table Food(
Transition_Matrix varbinary(512),
Name varchar(256),
Serving_Size int,
Calories int,
Calories_from_Fat int,
Total_Fat int,
Cholsterol int,
Sodium int,
Total_Carbohydrate int
);