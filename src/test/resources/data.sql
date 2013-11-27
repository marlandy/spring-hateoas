-- CARGA DE DATOS INICIALES PARA TESTS

-- equipos
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Real Madrid C.F.', 1902, 1);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('F.C. Barcelona', 1899, 2);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Atlético de Madrid', 1903, 3);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Málaga C.F.', 1948, 4);

-- estadios
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Santiago Bernabeu', 85454, 'Madrid', 5000);
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Camp Nou', 99354, 'Barcelona', 5001);
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Vicente Calderón', 54851, 'Madrid', 5002);

-- jugadores
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Cristiano Ronaldo', 28, 'Portugal', 172, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Xabi Alonso', 32, 'España', 12, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Sergio Ramos', 27, 'España', 24, 5000);


