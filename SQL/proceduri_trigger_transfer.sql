DROP TRIGGER IF EXISTS modifica_data_depozit;
delimiter //
CREATE TRIGGER modifica_data_depozit BEFORE UPDATE ON cont
FOR EACH ROW 
	BEGIN
		DECLARE IBAN_depozit, Iban_nou VARCHAR(200);
        SET Iban_nou = NEW.IBAN;
        UPDATE depozit
        SET depozit.data_ultim_mod = current_date()
        WHERE depozit.IBAN = Iban_nou;
    END; //
delimiter ;

DROP PROCEDURE IF EXISTS cerere_transfer;
delimiter //
CREATE PROCEDURE cerere_transfer(nume_client_destinatie VARCHAR(200),
								 IBAN_destinatie VARCHAR(200),
                                 suma double,
								 IBAN_sursa VARCHAR(200) -- contul din care se face tranzactia
								)
	BEGIN
		
		SET @IBAN_inexistent := (SELECT IBAN FROM cont_client WHERE cont_client.IBAN = IBAN_destinatie);
		SET @CNP_sursa := (SELECT CNP FROM cont_client WHERE cont_client.IBAN = IBAN_sursa);
		SET @nume_client_sursa := (SELECT CONCAT(nume, " ", prenume) FROM persoana WHERE persoana.CNP = @CNP_sursa);
		SET @tranzactii_online := (SELECT CLIENT.TRANZACTII_ONLINE FROM CLIENT WHERE CLIENT.CNP =@CNP_SURSA);
			IF @tranzactii_online = 1 THEN
					SET @sir1 := right(IBAN_sursa, length(IBAN_sursa)-2);
					SET @sir2 := left(@sir1, length(@sir1) - 10); -- am aflat de la ce banca e iban sursa
				   
					SET @comision = 0;
					IF(NOT IBAN_destinatie LIKE CONCAT('%',@sir2,'%'))THEN
						SET @comision = 1/100 * suma;
					END IF;

						IF(@IBAN_inexistent IS NOT null)THEN 
								SET @CNP_destinatie :=	(SELECT CNP FROM cont_client WHERE cont_client.IBAN = IBAN_destinatie);
								SET @nume_client_destinatie := (SELECT CONCAT(nume, " ", prenume) FROM persoana WHERE persoana.CNP = @CNP_destinatie);
								-- gasim contul din care se face transferul
								SET @suma := (SELECT cont.suma FROM cont WHERE(cont.IBAN = IBAN_sursa)); -- suma din contul sursa
								SET @total := @suma - suma;
								SET @total := @total - @comision;

								IF((@total>=0) AND (@nume_client_destinatie = nume_client_destinatie))THEN -- daca este suma de bani in cont, facem transfer, altfel ERROR
										UPDATE cont -- stergem banii din contul clientului care face transferul
										SET cont.suma = cont.suma - suma - @comision
										WHERE cont.IBAN = IBAN_sursa;
										-- cream tupla de transfer , cu statusul de created , si cu suma de bani transferata
										INSERT INTO transfer(status, nume_client_destinatie, nume_client_sursa, data_creare, suma, IBAN_destinatie, IBAN_sursa) 
													VALUES ('CREATED', nume_client_destinatie, @nume_client_sursa, CURRENT_DATE, suma, IBAN_destinatie, IBAN_sursa);
								ELSE 
										-- cream tupla de transfer , cu statusul de error deoarece nu exista suma de bani in contul sursa
										INSERT INTO transfer(status, nume_client_destinatie, nume_client_sursa, data_creare, suma, IBAN_destinatie, IBAN_sursa) 
													VALUES ('ERROR', nume_client_destinatie, @nume_client_sursa, CURRENT_DATE, suma, IBAN_destinatie, IBAN_sursa);
								END IF;
					 ELSE 
								-- cream tupla de transfer , cu statusul de error deoarece nu exista ibanul destinatie sau numele destinatarului nu corespunde cu contul
								INSERT INTO transfer(status, nume_client_destinatie, nume_client_sursa, data_creare, suma, IBAN_sursa) 
												VALUES ('ERROR', nume_client_destinatie, @nume_client_sursa, CURRENT_DATE, suma, IBAN_sursa);
					 END IF;
			ELSE
				SELECT "Acest client nu poate face tranzactii online";
			END IF;
    END; //
delimiter ; 

DROP PROCEDURE IF EXISTS aprobare_transfer;
delimiter //
CREATE PROCEDURE aprobare_transfer(transfer_id INT, CNP_angajat VARCHAR(200))
	BEGIN
		SET @IBAN_destinatie := (SELECT IBAN_destinatie FROM transfer t WHERE t.transfer_id = transfer_id);
		SET @suma := (SELECT suma FROM transfer t WHERE t.transfer_id = transfer_id);
        
		UPDATE transfer t
        SET t.CNP_angajat = CNP_angajat, t.STATUS = 'SUCCESSFUL' 
        WHERE t.transfer_id = transfer_id;
        
        UPDATE cont c
        SET c.suma = c.suma + @suma
        WHERE c.IBAN = @IBAN_destinatie;
        
    END; //
delimiter ; 