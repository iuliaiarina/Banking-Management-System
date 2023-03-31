DROP PROCEDURE IF EXISTS PLATA_FACTURI;
DELIMITER //
CREATE PROCEDURE PLATA_FACTURI(CNP_CLIENT VARCHAR(200),FACTURA_ID INT)
	BEGIN
		SET @IBAN_CONT :=(SELECT CR.IBAN FROM CONT_CURENT CR JOIN CONT_CLIENT CC ON CC.IBAN=CR.IBAN WHERE CC.CNP=CNP_CLIENT);
        set @cesumaam:= (select cont.suma from cont where cont.iban=@iban_cont);
        SET @suma:=(SELECT FACTURI.SUMA FROM FACTURI WHERE FACTURI.FACTURA_ID=FACTURA_ID);
        if(@cesumaam >= @suma)then
			CALL retrage_fonduri(@IBAN_CONT, @suma);
			DELETE FROM FACTURI WHERE FACTURI.FACTURA_ID=FACTURA_ID;
		end if;
    END//;
DELIMITER //

INSERT INTO FACTURI (NUME,SUMA,CNP)VALUES('APA',100,'1');
INSERT INTO FACTURI (NUME,SUMA,CNP)VALUES('CURENT',200,'1');
INSERT INTO FACTURI (NUME,SUMA,CNP)VALUES('INTERNET',45,'1');
INSERT INTO FACTURI (NUME,SUMA,CNP)VALUES('GAZ',30,1);
INSERT INTO FACTURI (NUME,SUMA,CNP)VALUES('APA',2000,'2');
INSERT INTO FACTURI (NUME,SUMA,CNP)VALUES('CURENT',199.99,'2');
INSERT INTO FACTURI (NUME,SUMA,CNP)VALUES('GAZ',2000,2);

SELECt* FROM facturi;

create view date_clienti as (select p.nume,p.prenume,p.adresa,p.contact,p.telefon,p.email,c.data_nastere,c.sursa_venit from client c join persoana p on c.cnp=p.cnp );
select* from date_clienti;

create view date_conturi as (select p.nume,p.prenume,p.cnp,ct.suma,ct.data_creare from (client c join persoana p on c.cnp=p.cnp) ,cont_client cc join cont ct on cc.iban=ct.iban where c.cnp=cc.cnp );
select* from date_conturi;

create view cereri_de_card as (select p.nume,p.prenume,cc.cerere_id,cc.cnp_angajat,cc.cnp_admin from (client c join persoana p on c.cnp=p.cnp join cerere_card cc on cc.cnp=c.cnp));
select* from cereri_de_card;



-- doua eventuri:
DROP PROCEDURE IF EXISTS calculeaza_economii;
DELIMITER //
CREATE PROCEDURE calculeaza_economii()
	BEGIN
        UPDATE cont
        SET CONT.SUMA = CONT.SUMA+0.02*CONT.SUMA
        WHERE IBAN IN(SELECT cont_economii.IBAN FROM CONT_ECONOMII);
    END//;
DELIMITER //

DROP PROCEDURE IF EXISTS calculeaza_depozit;
DELIMITER //
CREATE PROCEDURE calculeaza_depozit()
	BEGIN
		drop table if exists aux_tabel; 
		create table aux_tabel(
			perioada int,
            dobanda int,
            perioada2 int,
            iban varchar(200),
            i int auto_increment primary key
        );
		insert into aux_tabel(perioada,dobanda,perioada2,iban) select dob.perioada,dob.dobanda,DATEDIFF(current_date(),dep.data_ultim_mod),dep.iban from depozit dep join dobanda dob on dep.dobanda_id = dob.dobanda_id;
		set @max := (select max(i) from aux_tabel); 
        set @i:=1;
		WHILE(@i <= @max)DO
				set @zile:= (select perioada*30 from aux_tabel where i=@i);
                set @zile_trecute := (select perioada2 from aux_tabel where i=@i);
                set @ibandep :=(select aux_tabel.iban from aux_tabel where i=@i);
                set @cnp :=(select cc.cnp from cont_client cc where cc.iban=@ibandep);
                set @ibanbani := (select cc.iban from cont_curent cc join cont_client c on c.IBAN=cc.iban where c.cnp=@cnp);
                if(@zile<@zile_trecute) then
					set @dob:= (select dobanda/100 from aux_tabel where i=@i);
                    update cont
                    set suma = suma+suma*@dob
                    where cont.iban= @ibandep;
                    call lichidare_cont_bancar(@ibandep,@ibanbani);
                end if;
                set @i=@i+1;
		END WHILE;
		drop table aux_tabel;
   END//;
DELIMITER //

drop event if exists calculamdep;
CREATE EVENT calculamdep
ON SCHEDULE EVERY 1 second
STARTS CURRENT_TIMESTAMP
DO call calculeaza_depozit();

drop event if exists calculameco ;
CREATE EVENT calculameco
ON SCHEDULE EVERY 1 second
STARTS CURRENT_TIMESTAMP
DO call calculeaza_economii();