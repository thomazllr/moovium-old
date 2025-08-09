DELETE FROM ticket;
DELETE FROM message;
DELETE FROM user_role;
DELETE FROM role;
DELETE FROM session_group_member;
DELETE FROM session_group;
DELETE FROM movie_recommendation;
DELETE FROM friendship;
DELETE FROM seat_reservation;
DELETE FROM session;
DELETE FROM seat;
DELETE FROM theater;
DELETE FROM movie;
DELETE FROM users;

ALTER SEQUENCE movie_id_seq RESTART WITH 1;
ALTER SEQUENCE theater_id_seq RESTART WITH 1;
ALTER SEQUENCE seat_id_seq RESTART WITH 1;
ALTER SEQUENCE seat_reservation_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE friendship_id_seq RESTART WITH 1;
ALTER SEQUENCE movie_recommendation_id_seq RESTART WITH 1;
ALTER SEQUENCE session_group_id_seq RESTART WITH 1;
ALTER SEQUENCE session_group_member_id_seq RESTART WITH 1;
ALTER SEQUENCE role_id_seq RESTART WITH 1;
ALTER SEQUENCE message_id_seq RESTART WITH 1;

INSERT INTO movie (title, synopsis, duration, release_date, genre, age_rating, poster_url, status, featured, featured_until) VALUES
('Avatar: O Caminho da Água', 'Jake Sully vive com sua nova família formada no planeta de Pandora. Quando uma ameaça familiar retorna para terminar o que começou anteriormente, Jake deve trabalhar com Neytiri e o exército da raça Na''vi para proteger sua família.', 192, '2022-12-15', 'ACTION', '12', 'https://image.tmdb.org/t/p/w500/avatar2.jpg', 'active', true, '2025-09-01'),
('Top Gun: Maverick', 'Depois de mais de 30 anos de serviço como um dos principais aviadores da Marinha, Pete "Maverick" Mitchell está onde pertence, ultrapassando os limites como um piloto de testes corajoso.', 130, '2022-05-27', 'ACTION', '12', 'https://image.tmdb.org/t/p/w500/topgun.jpg', 'active', true, '2025-08-20'),
('Pantera Negra: Wakanda Para Sempre', 'A rainha Ramonda, Shuri, M''Baku, Okoye e as Dora Milaje lutam para proteger sua nação das potências mundiais intervenientes após a morte do rei T''Challa.', 161, '2022-11-11', 'ACTION', '12', 'https://image.tmdb.org/t/p/w500/pantera.jpg', 'active', false, null),
('Homem-Aranha: Sem Volta Para Casa', 'Peter Parker tem sua identidade secreta revelada e pede ajuda ao Doutor Estranho. Quando um feitiço para reverter o evento não sai como o esperado, o Homem-Aranha e seu companheiro dos Vingadores devem enfrentar inimigos de todas as realidades.', 148, '2021-12-17', 'ACTION', '12', 'https://image.tmdb.org/t/p/w500/spiderman.jpg', 'active', true, '2025-08-25'),
('Lightyear', 'A história da origem do herói de ação que inspirou o brinquedo: a lendária Patrulha Espacial Buzz Lightyear embarca em uma missão intergaláctica ao lado de recrutas ambiciosos e seu companheiro robô Sox.', 105, '2022-06-17', 'ANIMATION', 'L', 'https://image.tmdb.org/t/p/w500/lightyear.jpg', 'active', false, null),
('Doutor Estranho no Multiverso da Loucura', 'O Doutor Estranho desperta um mal indescritível ao lançar um feitiço proibido e deve enfrentar uma ameaça impensável.', 126, '2022-05-06', 'ACTION', '14', 'https://image.tmdb.org/t/p/w500/strange.jpg', 'active', false, null);

INSERT INTO theater (name, capacity, room_type) VALUES
('Sala IMAX 1', 150, 'IMAX'),
('Sala Premium XD', 120, 'XD'),
('Sala IMAX 2', 180, 'IMAX'),
('Sala Tradicional XD', 100, 'XD'),
('Sala IMAX 3', 120, 'IMAX');

INSERT INTO seat (theater_id, seat_number, row)
SELECT
    1 as theater_id,
    LPAD(seat_num::text, 2, '0') as seat_number,
    chr(65 + row_num) as row
FROM generate_series(0, 9) as row_num
CROSS JOIN generate_series(1, 15) as seat_num;

INSERT INTO seat (theater_id, seat_number, row)
SELECT
    2 as theater_id,
    LPAD(seat_num::text, 2, '0') as seat_number,
    chr(65 + row_num) as row
FROM generate_series(0, 7) as row_num
CROSS JOIN generate_series(1, 15) as seat_num;

INSERT INTO seat (theater_id, seat_number, row)
SELECT
    3 as theater_id,
    LPAD(seat_num::text, 2, '0') as seat_number,
    chr(65 + row_num) as row
FROM generate_series(0, 7) as row_num
CROSS JOIN generate_series(1, 10) as seat_num;

INSERT INTO seat (theater_id, seat_number, row)
SELECT
    4 as theater_id,
    LPAD(seat_num::text, 2, '0') as seat_number,
    chr(65 + row_num) as row
FROM generate_series(0, 11) as row_num
CROSS JOIN generate_series(1, 15) as seat_num;

INSERT INTO seat (theater_id, seat_number, row)
SELECT
    5 as theater_id,
    LPAD(seat_num::text, 2, '0') as seat_number,
    chr(65 + row_num) as row
FROM generate_series(0, 9) as row_num
CROSS JOIN generate_series(1, 10) as seat_num;

INSERT INTO session (id, movie_id, theater_id, session_time) VALUES
(gen_random_uuid(), 1, 1, CURRENT_DATE + INTERVAL '14 hours'),
(gen_random_uuid(), 1, 1, CURRENT_DATE + INTERVAL '19 hours 30 minutes'),
(gen_random_uuid(), 1, 4, CURRENT_DATE + INTERVAL '1 day 16 hours'),
(gen_random_uuid(), 1, 4, CURRENT_DATE + INTERVAL '1 day 21 hours'),

-- Top Gun: Maverick
(gen_random_uuid(), 2, 2, CURRENT_DATE + INTERVAL '15 hours'),
(gen_random_uuid(), 2, 2, CURRENT_DATE + INTERVAL '20 hours'),
(gen_random_uuid(), 2, 5, CURRENT_DATE + INTERVAL '1 day 18 hours'),

-- Pantera Negra: Wakanda Para Sempre
(gen_random_uuid(), 3, 3, CURRENT_DATE + INTERVAL '17 hours'),
(gen_random_uuid(), 3, 1, CURRENT_DATE + INTERVAL '1 day 19 hours 30 minutes'),

-- Homem-Aranha: Sem Volta Para Casa
(gen_random_uuid(), 4, 2, CURRENT_DATE + INTERVAL '16 hours 30 minutes'),
(gen_random_uuid(), 4, 5, CURRENT_DATE + INTERVAL '21 hours'),

-- Lightyear (sessão matinê)
(gen_random_uuid(), 5, 3, CURRENT_DATE + INTERVAL '1 day 10 hours'),
(gen_random_uuid(), 5, 5, CURRENT_DATE + INTERVAL '1 day 14 hours 30 minutes'),

-- Doutor Estranho
(gen_random_uuid(), 6, 4, CURRENT_DATE + INTERVAL '18 hours'),
(gen_random_uuid(), 6, 2, CURRENT_DATE + INTERVAL '1 day 20 hours 30 minutes');

INSERT INTO users (email, password_hash, full_name, nickname, avatar_url, bio, status) VALUES
('joao.silva@email.com', '$2a$10$dummyhash1', 'João Silva', 'joao_cine', 'https://avatar.example.com/joao.jpg', 'Fanático por filmes de ação e ficção científica!', 'active'),
('maria.santos@email.com', '$2a$10$dummyhash2', 'Maria Santos', 'maria_movies', 'https://avatar.example.com/maria.jpg', 'Apaixonada por cinema desde pequena 🎬', 'active'),
('pedro.oliveira@email.com', '$2a$10$dummyhash3', 'Pedro Oliveira', 'pedro_film', 'https://avatar.example.com/pedro.jpg', 'Sempre em busca do próximo blockbuster!', 'active'),
('ana.costa@email.com', '$2a$10$dummyhash4', 'Ana Costa', 'ana_cinema', null, 'Cinema é vida! Especialmente dramas e comédias.', 'active'),
('carlos.ferreira@email.com', '$2a$10$dummyhash5', 'Carlos Ferreira', 'carlao_movies', 'https://avatar.example.com/carlos.jpg', null, 'active');

INSERT INTO role (name) VALUES
('ADMIN'),
('USER'),
('MODERATOR');

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 3);

INSERT INTO seat_reservation (session_id, seat_id, status, reservation_expiration)
SELECT
    s.id as session_id,
    se.id as seat_id,
    'SOLD' as status,
    CURRENT_TIMESTAMP + INTERVAL '2 hours' as reservation_expiration
FROM session s
CROSS JOIN seat se
WHERE se.theater_id = s.theater_id
  AND se.row IN ('A', 'B', 'C')
  AND se.seat_number IN ('05', '06', '07', '08')
  AND s.session_time > CURRENT_TIMESTAMP
LIMIT 20; -- Apenas alguns assentos vendidos

INSERT INTO seat_reservation (session_id, seat_id, status, reservation_expiration)
SELECT
    s.id as session_id,
    se.id as seat_id,
    'RESERVED' as status,
    CURRENT_TIMESTAMP + INTERVAL '15 minutes' as reservation_expiration
FROM session s
CROSS JOIN seat se
WHERE se.theater_id = s.theater_id
  AND se.row IN ('D', 'E')
  AND se.seat_number IN ('09', '10', '11')
  AND s.session_time > CURRENT_TIMESTAMP
LIMIT 10;

INSERT INTO friendship (user_id_1, user_id_2, status) VALUES
(1, 2, 'ACCEPTED'),
(1, 3, 'ACCEPTED'),
(2, 4, 'ACCEPTED'),
(3, 4, 'PENDING'),
(1, 5, 'ACCEPTED');