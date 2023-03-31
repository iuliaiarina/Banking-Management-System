DROP PROCEDURE IF EXISTS PROC_10 ;
DELIMITER //
CREATE PROCEDURE PROC_10(CNP VARCHAR(200))
	BEGIN 
		INSERT INTO CLIENT VALUES(current_date(),'angajat la banca','0','1',CNP);
    END //
DELIMITER ;

DROP PROCEDURE IF EXISTS Creare_Client;
delimiter //
CREATE PROCEDURE  Creare_Client( NUME VARCHAR(200),
							  PRENUME VARCHAR(200),
							  PAROLA VARCHAR(200),
							  ADRESA VARCHAR(200),
							  TELEFON VARCHAR(200),
							  EMAIL VARCHAR(200),
							  CONTACT VARCHAR(200),
							  CNP VARCHAR(200),
                              DATA_NASTERE DATE, 
                              SURSA_VENIT VARCHAR(200),
                              TRANZACTII_ONLINE BOOL) 
	BEGIN 
		INSERT INTO persoana VALUES (nume, prenume, parola, adresa, telefon, email, contact, CNP);
		INSERT INTO CLIENT VALUES (data_nastere, sursa_venit, 0, tranzactii_online, CNP);
    END; //
delimiter ;

DROP PROCEDURE IF EXISTS Creare_Angajat; 
delimiter //
CREATE PROCEDURE  Creare_Angajat( NUME VARCHAR(200),
							      PRENUME VARCHAR(200),
							      PAROLA VARCHAR(200),
							      ADRESA VARCHAR(200),
							      TELEFON VARCHAR(200),
							      EMAIL VARCHAR(200),
							      CONTACT VARCHAR(200),
								  CNP VARCHAR(200),
                                  NORMA INT,
                                  SALARIU double,
                                  Departament ENUM('HR', 'IT', 'FUNCTIONAR'),
                                  SUCURSALA VARCHAR(200)) 
	BEGIN 
		INSERT INTO persoana VALUES (nume, prenume, parola, adresa, telefon, email, contact, CNP);
		INSERT INTO angajat VALUES (norma, salariu, departament, sucursala, CNP);
    END; //
delimiter ;

DROP PROCEDURE IF EXISTS Creare_Admin ;
delimiter //
CREATE PROCEDURE  Creare_Admin( NUME VARCHAR(200),
								PRENUME VARCHAR(200),
								PAROLA VARCHAR(200),
								ADRESA VARCHAR(200),
								TELEFON VARCHAR(200),
								EMAIL VARCHAR(200),
								CONTACT VARCHAR(200),
								CNP VARCHAR(200),
								NORMA INT,
								SALARIU double,
								Departament ENUM('HR', 'IT', 'FUNCTIONAR'),
								SUCURSALA VARCHAR(200)) 
	BEGIN 
		INSERT INTO persoana VALUES (nume, prenume, parola, adresa, telefon, email, contact, CNP);
		INSERT INTO angajat VALUES (norma, salariu, departament, sucursala, CNP);
        INSERT INTO administrator VALUES (CNP); 
    END; //
delimiter ;

DROP PROCEDURE if exists cerere_card;
delimiter //
	CREATE PROCEDURE cerere_card (CNP_client VARCHAR(200))
	BEGIN
		SET @IBAN := (SELECT cont_curent.IBAN FROM cont_curent JOIN CONT_CLIENT ON( cont_curent.IBAN = cont_CLIENT.IBAN)WHERE cont_CLIENT.CNP = CNP_client);
		INSERT INTO cerere_card(CNP, IBAN) VALUES (CNP_client, @IBAN);
    END; //
delimiter ;

DROP PROCEDURE IF EXISTS aprobare_cerere_card_angajat;
delimiter //
	CREATE PROCEDURE aprobare_cerere_card_angajat ( CNP_angajat VARCHAR(200),cerere_id INT)
	BEGIN
		UPDATE cerere_card cd
        SET cd.CNP_angajat = CNP_angajat
        WHERE cd.cerere_id = cerere_id;
    END; //
delimiter ;

delimiter //
	CREATE PROCEDURE aprobare_cerere_card_admin (CNP_admin VARCHAR(200), cerere_id INT)
	BEGIN
		UPDATE cerere_card cd
        SET cd.CNP_admin = CNP_admin
        WHERE cd.cerere_id = cerere_id;
    END; //
delimiter ;


DROP PROCEDURE IF EXISTS lichidare_cont_client;
delimiter //
CREATE PROCEDURE lichidare_cont_client (CNP VARCHAR(200))
		BEGIn
            SET @IBAN_cont_curent := (SELECT cc.IBAN FROM cont_curent cc JOIN cont_client c ON (c.IBAN = cc.IBAN) WHERE c.CNP = CNP);
			IF(@IBAN_cont_curent IS NOT NULL)THEN  -- stergem contul curent
				DELETE FROM transfer where transfer.IBAN_destinatie=@IBAN_cont_curent;  -- prima oara transferu ca are fk
                DELETE FROM transfer where transfer.IBAN_sursa=@IBAN_cont_curent;
            
				DELETE FROM cont_curent cc    -- chiar contul
                WHERE cc.IbAN = @IBAN_cont_curent;
                
                DELETE FROM cont_client   -- legatura
                WHERE cont_client.IBAN = @IBAN_cont_curent;
                
                DELETE FROM cont 
                WHERE cont.IBAN = @IBAN_cont_curent;
                
                 UPDATE client    
                 SET client.nr_conturi = client.nr_conturi - 1
                 WHERE client.CNP = CNP; 
            END IF;
            
            SET @nr_conturi := (SELECT nr_conturi FROM client WHERE client.CNP = CNP);
            SET @index = 1;
            
            DROP TABLE IF EXISTS IBAN_conturi;
            
            CREATE TABLE IBAN_conturi(   -- o tabela aux
				n INT PRIMARY KEY AUTO_INCREMENT,
				IBAN VARCHAR(200)
            );
            
            INSERT INTO IBAN_conturi (IBAN)
            SELECT cc.IBAN FROM cont_client cc 
            WHERE cc.CNP = CNP;
            
            SELECT * FROM IBAN_conturi;
            WHILE(@index <= @nr_conturi)DO
            
				SET @IBAN_de_sters := (SELECT IBAN FROM IBAN_conturi WHERE IBAN_conturi.n = @index);
                SET @index := @index + 1; -- crestem indexul pentru stergerea conturilor
                
				CALL lichidare_cont_bancar(@IBAN_de_sters, NULL); -- IBAN DE STERS E PRIMUL
			END WHILE;
            
            DROP TABLE IF EXISTS IBAN_conturi;
            
            delete from lista_contacte where lista_contacte.cnp=cnp;
            delete from cerere_card where cerere_card.cnp=cnp;
			delete from client  where client.cnp=cnp;
            delete from persoana  where persoana.cnp=cnp;
      END; //
		
delimiter ;