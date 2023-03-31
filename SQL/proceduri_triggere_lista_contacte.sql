DROP PROCEDURE IF EXISTS adauga_in_lista;
delimiter //
	CREATE PROCEDURE adauga_in_lista (comentariu VARCHAR(200), -- informatii
                                        nume_client_destinatie VARCHAR(200),
                                        IBAN_destinatie VARCHAR(200),
                                        CNP_sursa VARCHAR(200) )
		BEGIN
        SET @Exista := (SELECT IBAN FROM cont WHERE cont.IBAN = IBAN_destinatie);
        IF(@Exista IS NOT NULL) THEN
		 	SET @CNP_destinatie := (SELECT CNP FROM cont_client WHERE cont_client.IBAN = IBAN_destinatie);
		 	SET @Client_destinatie := (SELECT CONCAT(nume, ' ', prenume) FROM  persoana WHERE persoana.CNP = @CNP_destinatie);
            
            IF(@Client_destinatie = nume_client_destinatie)THEN
				INSERT INTO lista_contacte(comentariu, nume_client_destinatie, IBAN_destinatie, CNP) 
					VALUES(comentariu, nume_client_destinatie, IBAN_destinatie, CNP_sursa);
			else select 'error';
		 	END IF;
		else select 'error';
		END IF;
        END; //
delimiter ;

