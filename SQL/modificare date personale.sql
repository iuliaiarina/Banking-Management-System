drop procedure if exists modifca_persoana;
delimiter //                
CREATE PROCEDURE  modifca_persoana ( NUME VARCHAR(200),
									PRENUME VARCHAR(200),
									PAROLA VARCHAR(200),
									ADRESA VARCHAR(200),
									TELEFON VARCHAR(200),
									EMAIL VARCHAR(200),
									CONTACT VARCHAR(200),
									CNP VARCHAR(200)
									)
	BEGIN 
		if nume IS NOT NULL then
			SELECT NUME;
			UPDATE PERSOANA	
            SET PERSOANA.NUME = NUME
            WHERE PERSOANA.CNP = CNP;
		end if;
        if(PRENUME is not NULL)then
			UPDATE PERSOANA	
            SET PERSOANA.PRENUME =PRENUME
            WHERE PERSOANA.CNP = CNP;
        end if;
        if(PAROLA IS NOT NULL)then
			UPDATE PERSOANA	
            SET PERSOANA.PAROLA = PAROLA
            WHERE PERSOANA.CNP = CNP;
        end if;
        if(ADRESA IS NOT NULL)then
			UPDATE PERSOANA	
            SET PERSOANA.ADRESA = ADRESA
            WHERE PERSOANA.CNP = CNP;
        end if;
        if(TELEFON IS NOT NULL)then
			UPDATE PERSOANA	
            SET PERSOANA.TELEFON = TELEFON
            WHERE PERSOANA.CNP = CNP;
        end if;
        if(EMAIL IS NOT NULL)then
			UPDATE PERSOANA	
            SET PERSOANA.EMAIL = EMAIL
            WHERE PERSOANA.CNP = CNP;
        end if;
        IF CONTACT IS NOT NULL then
			UPDATE PERSOANA	
            SET PERSOANA.CONTACT = CONTACT
            WHERE PERSOANA.CNP = CNP;
		END IF;
    END; //
delimiter ;