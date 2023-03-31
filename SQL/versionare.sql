delimiter //
CREATE PROCEDURE versiunea2()
	BEGIN
		CREATE TABLE curs_valutar_BNR(
			VALUTA VARCHAR(20),
            PRET_IN_LEI NUMERIC(7, 4)
        );
        
        INSERT INTO versiuni VALUES (2, 'DROP TABLE curs_valutar_BNR' );
	END; //
delimiter ;

delimiter //
CREATE PROCEDURE versiunea3()
	BEGIN
		ALTER TABLE curs_valutar_BNR
        MODIFY VALUTA TEXT NOT NULL;
        
        INSERT INTO VERSIUNI VALUES ('3', 'ALTER TABLE curs_valutar_BNR MODIFY VALUTA VARCHAR(20) NOT NULL');
    END; //
delimiter ;

delimiter //
CREATE PROCEDURE versiunea4()
	BEGIN 
		INSERT INTO curs_valutar_BNR VALUES ('EUR', '4.9493');
        INSERT INTO curs_valutar_BNR VALUES ('USD', '4.3821');
        INSERT INTO curs_valutar_BNR VALUES ('GBP', '5.8081');
        
        INSERT INTO versiuni VALUES ('4', 'TRUNCATE TABLE curs_valutar_BNR');
    END; //
delimiter ;

delimiter //
CREATE PROCEDURE versionare( versiune_dorita INT )
	BEGIN
		SET @versiune_veche = (SELECT MAX(id_versiune) FROM versiuni);
        SET @versiune_noua = versiune_dorita;
        
        IF(@versiune_noua > @versiune_veche)THEN
			
			SET @versiune_veche = @versiune_veche + 1;
            WHILE (@versiune_veche <= @versiune_noua) DO
				SELECT @versiune_veche;
				SET @procedura = CONCAT('versiunea', @versiune_veche);
				SELECT @procedura;
                
                SET @versiune_veche = @versiune_veche + 1;
				SET @x = CONCAT('CALL ', @procedura);

				prepare stmt FROM @x;
				execute stmt;
				deallocate prepare stmt;
                
                
            END WHILE;
            
        ELSE IF(@versiune_noua < @versiune_veche)THEN
					SET @versiune_veche = @versiune_veche;
					WHILE (@versiune_veche > @versiune_noua) DO
						SET @procedura = (SELECT mesaj FROM versiuni where versiuni.id_versiune =@versiune_veche);
						SELECT @procedura;
						SET @x = @procedura;

						prepare stmt FROM @x;
						execute stmt;
						deallocate prepare stmt;
						
                        DELETE FROM versiuni where versiuni.id_versiune = @versiune_veche;
                        
						SET@versiune_veche = @versiune_veche - 1;
					END WHILE;
				END IF;
			END IF;
    END; //
delimiter ;
