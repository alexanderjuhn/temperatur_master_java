CREATE TABLE statusdata.room (
	id int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT room_pk PRIMARY KEY (id),
	CONSTRAINT room_pk_2 UNIQUE ("name")
);


CREATE TABLE statusdata.roomdata (
	id int4 NOT NULL,
	room_id int4 NOT NULL,
	temperature numeric(4,1) NULL DEFAULT 0,
	humidity numeric(4,1) NULL DEFAULT 0,
	datecreated timestamp NOT NULL,
	CONSTRAINT roomdata_pk PRIMARY KEY (id)
);


-- statusdata.roomdata foreign keys

ALTER TABLE statusdata.roomdata ADD CONSTRAINT roomdata_fk FOREIGN KEY (room_id) REFERENCES statusdata.room(id) ON DELETE RESTRICT ON UPDATE RESTRICT;