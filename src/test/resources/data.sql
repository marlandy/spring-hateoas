-- CARGA DE DATOS INICIALES PARA TESTS

-- estadios
INSERT INTO stadiums (name, capacity, city) VALUES ('Santiago Bernabeu', 85454, 'Madrid');
INSERT INTO stadiums (name, capacity, city) VALUES ('Camp Nou', 99354, 'Barcelona');
INSERT INTO stadiums (name, capacity, city) VALUES ('Vicente Calderón', 54851, 'Madrid');
INSERT INTO stadiums (name, capacity, city) VALUES ('La Rosaleda', 28963, 'Málaga');

-- equipos
INSERT INTO teams (name, foundation_year, ranking_position, stadium_id) VALUES ('Real Madrid C.F.', 1902, 1, 1000);
INSERT INTO teams (name, foundation_year, ranking_position, stadium_id) VALUES ('F.C. Barcelona', 1899, 2, 1001);
INSERT INTO teams (name, foundation_year, ranking_position, stadium_id) VALUES ('Atlético de Madrid', 1903, 3, 1002);

-- jugadores
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Cristiano Ronaldo', 28, 'Portugal', 172, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Xabi Alonso', 32, 'España', 12, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Sergio Ramos', 27, 'España', 24, 5000);


