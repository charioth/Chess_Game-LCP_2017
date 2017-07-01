DROP TABLE if exists save_game;
CREATE TABLE save_game (
	name varchar(16) not null,
	save_date varchar(10) not null,
	turn int not null,
	
	PRIMARY KEY(name)
);

DROP TABLE if exists piece;
CREATE TABLE piece (
	id int not null auto_increment,
	game_name varchar(30) references save_game(name),
	
	coord_row int not null,
	coord_column int not null,
	piece_type int not null,
	moved boolean not null,
	piece_color int not null,
	piece_index int not null,

	constraint c_position unique(game_name, piece_color, piece_index),
	PRIMARY KEY(id)
);

INSERT INTO piece (game_name, coord_row, coord_column, piece_type, moved, piece_color, piece_index) VALUES ('teste', 1, 1, 1, false, 1, 1);

INSERT INTO save_game(name, save_date, turn) VALUES ('teste', '10/10/2017', 1);


SELECT * FROM piece WHERE game_name = 'teste';

SELECT name FROM save_game;

DELETE FROM piece WHERE game_name = 'TEEESTE';