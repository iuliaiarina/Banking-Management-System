DROP PROCEDURE IF EXISTS adauga_fonduri;
delimiter //
	CREATE PROCEDURE adauga_fonduri (IBAN VARCHAR(200),  suma double )
		BEGIN
			UPDATE cont
            SET cont.suma = cont.suma + suma
            WHERE cont.IBAN = IBAN;
        END; //
delimiter ;

-- CALL adauga_fonduri ( 'ROBRD1000000001',2.20);
-- select * from cont;
-- select* from cont_client;
DROP PROCEDURE IF EXISTS retrage_fonduri;
delimiter //
	CREATE PROCEDURE retrage_fonduri (IBAN VARCHAR(200), suma double )
		BEGIN
        SET @suma := (SELECT cont.suma FROM cont WHERE cont.IBAN = IBAN);
			IF(@suma>=suma)THEN
				UPDATE cont
				SET cont.suma = cont.suma - suma
				WHERE cont.IBAN = IBAN;
			END IF;
        END; //
delimiter ;





































