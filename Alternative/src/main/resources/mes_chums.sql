CREATE TABLE Contact (
    id_contact INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    isFavoris BOOLEAN DEFAULT FALSE
);

CREATE TABLE Adresse (
    id_adresse INTEGER PRIMARY KEY AUTOINCREMENT,
    rue TEXT,
    ville TEXT,
    codePostal TEXT,
    pays TEXT,
    latitude REAL,
    longitude REAL,
    contact_id INTEGER,
    FOREIGN KEY (contact_id) REFERENCES Contact(id_contact)
);