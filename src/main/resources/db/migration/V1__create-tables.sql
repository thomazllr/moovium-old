CREATE TABLE movie
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    synopsis       TEXT,
    duration       INTEGER      NOT NULL,
    release_date   DATE         NOT NULL,
    genre          VARCHAR(100) CHECK (genre IN
                                       ('ACTION', 'ADVENTURE', 'ANIMATION', 'COMEDY', 'CRIME', 'DRAMA', 'FANTASY',
                                        'HORROR', 'MYSTERY', 'ROMANCE', 'SCI_FI', 'THRILLER', 'DOCUMENTARY', 'MUSICAL',
                                        'WAR', 'WESTERN')),
    age_rating     VARCHAR(10),
    poster_url     VARCHAR(255),
    status         VARCHAR(20) DEFAULT 'active',
    featured    BOOLEAN     DEFAULT FALSE,
    featured_until DATE,
    created_at     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE theater
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    capacity   INTEGER,
    room_type  VARCHAR(10)  NOT NULL CHECK (room_type IN ('IMAX', 'XD', 'D-BOX')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE seat
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    theater_id  BIGINT     NOT NULL REFERENCES theater (id),
    seat_number VARCHAR(2) NOT NULL,
    row         VARCHAR(2) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (theater_id, row, seat_number)
);

CREATE TABLE session
(
    id             UUID PRIMARY KEY NOT NULL,
    movie_id     BIGINT REFERENCES movie (id),
    theater_id   BIGINT    NOT NULL REFERENCES theater (id),
    session_time TIMESTAMP NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE seat_reservation
(
    id                     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    session_id             UUID      NOT NULL REFERENCES session (id),
    seat_id                BIGINT      NOT NULL REFERENCES seat (id),
    status                 VARCHAR(10) NOT NULL CHECK (status IN ('AVAILABLE', 'RESERVED', 'SOLD')),
    reservation_expiration TIMESTAMP   NOT NULL,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (session_id, seat_id)
);

CREATE TABLE "users"
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    nickname      VARCHAR(50),
    avatar_url    VARCHAR(255),
    bio           TEXT,
    status        VARCHAR(20) DEFAULT 'active',
    created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE friendship
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id_1  BIGINT      NOT NULL REFERENCES "users" (id),
    user_id_2  BIGINT      NOT NULL REFERENCES "users" (id),
    status     VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id_1, user_id_2)
);

CREATE TABLE movie_recommendation
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    from_user_id BIGINT NOT NULL REFERENCES "users" (id),
    to_user_id   BIGINT NOT NULL REFERENCES "users" (id),
    movie_id     BIGINT NOT NULL REFERENCES movie (id),
    message      TEXT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE session_group
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    creator_id BIGINT       NOT NULL REFERENCES "users" (id),
    session_id UUID       NOT NULL REFERENCES session (id),
    status     VARCHAR(20) CHECK (status IN ('PLANNING', 'CONFIRMED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE session_group_member
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    group_id   BIGINT NOT NULL REFERENCES session_group (id),
    user_id    BIGINT NOT NULL REFERENCES "users" (id),
    status     VARCHAR(20) CHECK (status IN ('INVITED', 'ACCEPTED', 'DECLINED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (group_id, user_id)
);

CREATE TABLE ticket
(
    id             UUID PRIMARY KEY NOT NULL,
    reservation_id BIGINT         NOT NULL REFERENCES seat_reservation (id),
    user_id        BIGINT         NOT NULL REFERENCES "users" (id),
    price          DECIMAL(18, 2) NOT NULL,
    qr_code        TEXT           NOT NULL,
    status_ticket  VARCHAR(20)    NOT NULL CHECK (status_ticket IN ('VALID', 'USED', 'CANCELLED', 'EXPIRED')),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE role
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_role
(
    user_id BIGINT NOT NULL REFERENCES "users" (id),
    role_id BIGINT NOT NULL REFERENCES role (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE message
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sender_id   BIGINT NOT NULL REFERENCES "users" (id),
    receiver_id BIGINT NOT NULL REFERENCES "users" (id),
    content     TEXT   NOT NULL,
    sent_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at     TIMESTAMP
);
