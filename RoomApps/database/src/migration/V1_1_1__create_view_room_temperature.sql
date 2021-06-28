CREATE OR REPLACE VIEW room_temperature
AS
	SELECT r."name" , rd.datecreated , rd.temperature , rd.humidity 
	FROM statusdata.roomdata rd
	inner join statusdata.room r on rd.room_id = r.id 
