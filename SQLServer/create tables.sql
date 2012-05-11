use [Nutrition-App]
go

create table Device (
ID int  PRIMARY KEY identity(0,1),
times_used int,
times_correct int
);

create table Food(
Transition_Matrix varbinary(max),
Name varchar(256),
Calories float,
Calories_from_Fat float,
Total_Fat float,
Sodium float,
Carbohydrates float,
Fiber float,
Sugar float,
Protein float
);