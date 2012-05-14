use [Nutrition-App]
go

CREATE PROCEDURE [dbo].addFoodItem
(@Transition_Matrix varbinary(max),
@Name varchar(256),
@NameSpanish varchar(256),
@NameFrench varchar(256),
@NameGerman varchar(256),
@Calories float,
@Calories_from_Fat float,
@Total_Fat float,
@Sodium float,
@Carbohydrates float,
@Fiber float,
@Sugar float,
@Protein float)

AS
insert into Food values (@Transition_Matrix,@Name,@NameSpanish,@NameFrench,@NameGerman,@Calories,@Calories_from_Fat,@Total_Fat,@Sodium,@Carbohydrates,@Fiber,@Sugar,@Protein)
go

CREATE PROCEDURE [dbo].getFood
as
select * from Food
go