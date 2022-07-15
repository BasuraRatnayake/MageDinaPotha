CREATE TABLE IF NOT EXISTS loginD(
	uniqCode            INTEGER PRIMARY KEY AUTOINCREMENT,
        password        VARCHAR2(20)    NOT NULL,
        secQuestion     VARCHAR2(20)    NOT NULL,
        answer          VARCHAR2(20)    NOT NULL,
        lastLogged		DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS personalD(
	uniqCode			INTEGER PRIMARY KEY,
        fname			VARCHAR2(20)	NOT NULL,
        lname			VARCHAR2(20)	NOT NULL,
        dob				DATE		NOT NULL,
        country			VARCHAR2(20)	NOT NULL,
        sex				VARCHAR2(1)     DEFAULT '1',
        email			VARCHAR2(40)	NOT NULL,
        mobile			VARCHAR2(20)	NOT NULL,
        phone			VARCHAR2(20)	NOT NULL,
        FOREIGN KEY (uniqCode) REFERENCES loginD (uniqCode)
);
CREATE TABLE IF NOT EXISTS textEntries(
	uID					INTEGER PRIMARY KEY   AUTOINCREMENT,
	logged				DATE            NOT NULL,
	filename			VARCHAR2(10)	NOT NULL,
	UNIQUE (logged)
);
CREATE TABLE IF NOT EXISTS audioEntries(
	uID					INTEGER PRIMARY KEY   AUTOINCREMENT,
	logged				DATE            NOT NULL,
	filename			VARCHAR2(10)	NOT NULL,
	UNIQUE (logged)
);
CREATE TABLE IF NOT EXISTS videoEntries(
	uID					INTEGER PRIMARY KEY   AUTOINCREMENT,
	logged				DATE            NOT NULL,
	filename			VARCHAR2(10)	NOT NULL,
	UNIQUE (logged)
);