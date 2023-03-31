DROP PROCEDURE IF EXISTS creare_cont_curent;
delimiter //
CREATE PROCEDURE creare_cont_curent (nume_banca ENUM('BTRL', 'ING', 'BRD', 'RZBR'), CNP varchar(200))
	BEGIN
		SET @nr_conturi := (SELECT nr_conturi FROM client WHERE client.CNP = CNP);
		IF(@nr_conturi <= 4)THEN
					SET @ok = (SELECT cc.IBAN FROM cont_curent cc JOIN cont_client c ON( c.IBAN = cc.IBAN)WHERE c.CNP = CNP);
					IF(@ok IS NULL) THEN
								SET @MAXIM := (SELECT MAX(RIGHT(IBAN, 10)) FROM CONT);
								select @maxim;
								SET @IBAN := RIGHT(@MAXIM,10)+1;
								select @iban;
								if @MAXIM IS NULL THEN
									SET @IBAN := 1000000001;
								END IF;
							
								SELECT @IBAN := CONCAT('RO', nume_banca, @IBAN); -- aici avem IBANU generat UNIC
							
								INSERT INTO CONT VALUES(0, current_date(), @IBAN);
								INSERT INTO cont_curent VALUES(@IBAN);
							
								UPDATE client 
								SET nr_conturi = nr_conturi + 1
								WHERE client.CNP = CNP;
							
								INSERT INTO cont_client VALUES (CNP, @IBAN);
							
					END IF;
		ELSE SELECT "NUMARUL MAXIM DE CONTURI A FOST ATINS";
		END IF;
    END; //
delimiter ; 

DROP PROCEDURE IF EXISTS creare_cont_economii;
delimiter //
CREATE PROCEDURE creare_cont_economii (nume_banca ENUM('BTRL', 'ING', 'BRD', 'RZBR'),CNP varchar(200))
	BEGIN
		SET @nr_conturi := (SELECT nr_conturi FROM client WHERE client.CNP = CNP);
		IF(@nr_conturi <= 4)THEN
				
					SET @MAXIM := (SELECT MAX(RIGHT(IBAN, 10)) FROM CONT);
					SELECT @maxim;
					SET @IBAN := RIGHT(@MAXIM,10)+1;
					SELECT @iban;
					if @MAXIM IS NULL THEN
						SET @IBAN := 1000000001;
					END IF;
				
					SELECT @IBAN:=CONCAT('RO', nume_banca, @IBAN); -- aici avem IBANU generat UNIC
				
					INSERT INTO CONT VALUES(0, current_date(), @IBAN);
					INSERT INTO cont_ECONOMII VALUES(5,@IBAN);
				
				
					UPDATE client 
						SET nr_conturi = nr_conturi + 1
						WHERE client.CNP = CNP;
						INSERT INTO cont_client VALUES (CNP, @IBAN);
                        
		ELSE SELECT "NUMARUL MAXIM DE CONTURI A FOST ATINS";
        END IF;
    END; //
delimiter ; 

DROP PROCEDURE IF EXISTS creare_cont_depozit;
delimiter //
CREATE PROCEDURE creare_cont_depozit (NUME_BANCA ENUM('BTRL', 'ING', 'BRD', 'RZBR'),
									  CNP varchar(200),
                                      DOBANDA_ID INT
									 )
	BEGIN
		SET @nr_conturi := (SELECT nr_conturi FROM client WHERE client.CNP = CNP);
		IF(@nr_conturi <= 4)THEN
				
					SET @MAXIM := (SELECT MAX(RIGHT(IBAN, 10)) FROM CONT);
					select @maxim;
					SET @IBAN := RIGHT(@MAXIM,10)+1;
					select @iban;
					if @MAXIM IS NULL THEN
						SET @IBAN := 1000000001;
					END IF;
				
					SELECT @IBAN := CONCAT('RO', nume_banca, @IBAN); -- aici avem IBANU generat UNIC
				
					INSERT INTO CONT VALUES(0, CURRENT_DATE(), @IBAN);
					INSERT INTO depozit VALUES(CURRENT_DATE(), @IBAN, DOBANDA_ID);
				
					UPDATE client 
					SET nr_conturi = nr_conturi + 1
					WHERE client.CNP = CNP;
				
					INSERT INTO cont_client VALUES (CNP, @IBAN);
                    
		ELSE SELECT "NUMARUL MAXIM DE CONTURI A FOST ATINS";
		END IF;
        
    END; //
delimiter ; 

delimiter //
CREATE PROCEDURE lichidare_cont_bancar (
									IBAN_de_sters VARCHAR(200),
                                    IBAN_de_pus_bani VARCHAR(200)
								)
		BEGIN
			SET @clientposesor := (SELECT cc.CNP FROM cont_client cc WHERE IBAN = IBAN_de_sters);
            SET @sumadincont := (SELECT c.suma FROM cont c WHERE c.IBAN = IBAN_de_sters);
            SET @sumadincont := @sumadincont - 0.02*@sumadincont; 
            SET @isdepozit := (SELECT d.IBAN FROM depozit d WHERE d.IBAN = IBAN_de_sters);
            IF(@isdepozit IS NOT NULL)THEN  -- avem un depozit de lichidat:
				CALL adauga_fonduri(IBAN_de_pus_bani, @sumadincont);
                
				DELETE FROM transfer where transfer.IBAN_destinatie=IBAN_de_sters;  -- prima oara transferu ca are fk
                DELETE FROM transfer where transfer.IBAN_sursa=IBAN_de_sters;
                
                DELETE FROM depozit 
                WHERE depozit.IBAN = IBAN_de_sters;
                
                DELETE FROM cont_client
                WHERE cont_client.IBAN = IBAN_de_sters;
                
                DELETE FROM cont 
                WHERE cont.IBAN = IBAN_de_sters;
                
                UPDATE client
                SET client.nr_conturi = client.nr_conturi - 1
                WHERE client.CNP = @clientposesor;
                
            ELSE SET @iseconomii := (SELECT e.IBAN FROM cont_economii e WHERE e.IBAN = IBAN_de_sters); 
				 IF(@iseconomii IS NOT NULL)THEN  -- avem un cont de economii de lichidat
					CALL adauga_fonduri(IBAN_de_pus_bani, @sumadincont);
                    
                    DELETE FROM transfer where transfer.IBAN_destinatie=IBAN_de_sters;  -- prima oara transferu ca are fk
					DELETE FROM transfer where transfer.IBAN_sursa=IBAN_de_sters;
                    
                    DELETE FROM cont_economii e 
					WHERE e.IBAN = IBAN_de_sters;
                    
                    DELETE FROM cont_client
					WHERE cont_client.IBAN = IBAN_de_sters;
                    
					DELETE FROM cont 
					WHERE cont.IBAN = IBAN_de_sters;
                
					UPDATE client
					SET client.nr_conturi = client.nr_conturi - 1
					WHERE client.CNP = @clientposesor;
                
					
                 END IF;
			END IF;
        END; //
delimiter ;