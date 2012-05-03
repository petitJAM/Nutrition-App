use [Nutrition-App]
go

CREATE PROCEDURE [dbo].getDevice
(@DeviceID int)
AS
select * from Device where ID = @DeviceID
go

CREATE PROCEDURE [dbo].updateDevice
(@DeviceID int, @times_used int, @times_correct int)
AS
update Device set times_used =@times_used, times_correct = @times_correct where ID = @DeviceID
go

CREATE PROCEDURE [dbo].addFoodItem
(@Transition_Matrix varbinary(512),
@Name varchar(256),
@Calories float,
@Calories_from_Fat float,
@Total_Fat float,
@Sodium float,
@Carbohydrates float,
@Fiber float,
@Sugar float,
@Protein float)

AS
insert into Food values (@Transition_Matrix,@Name,@Calories,@Calories_from_Fat,@Total_Fat,@Sodium,@Carbohydrates,@Fiber,@Sugar,@Protein)
go

CREATE PROCEDURE [dbo].getFood
as
select * from Food
go

CREATE PROCEDURE [dbo].addDevice
as
insert into Device values (0,0)
select max(ID) from Device
go