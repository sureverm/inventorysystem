DROP TABLE IF EXISTS article;
CREATE TABLE article
(
    art_id   SERIAL PRIMARY KEY,
    art_number varchar(50) NOT NULL,
    art_type varchar(50) NOT NULL,
    name     varchar(100) NOT NULL,
    stock    integer NOT NULL,
    price    numeric(8, 2) NOT NULL,
    sellable boolean NOT NULL
);
DROP TABLE IF EXISTS article_relationship;
CREATE TABLE article_relationship
(
    relationship_id SERIAL PRIMARY KEY,
    art_parent_id  integer NOT NULL REFERENCES article (art_id),
    art_child_id integer NOT NULL REFERENCES article (art_id),
    quantity   integer NOT NULL
);
