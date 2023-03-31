DROP USER IF EXISTS 'client'@'localhost';
DROP USER IF EXISTS 'angajat'@'localhost';
DROP USER IF EXISTS 'admin'@'localhost';

CREATE USER 'client'@'localhost' IDENTIFIED by '0001';
CREATE USER 'angajat'@'localhost'IDENTIFIED by '0002';
CREATE USER 'admin'@'localhost'IDENTIFIED by '0003';
select user from mysql.user;


GRANT SELECT ON Bancar.* TO 'client'@'localhost';
GRANT INSERT ON Bancar.* TO 'client'@'localhost';
GRANT UPDATE ON Bancar.* TO 'client'@'localhost';
GRANT DELETE ON Bancar.* TO 'client'@'localhost';
GRANT SHOW VIEW ON Bancar.* TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.adauga_in_lista TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.PLATA_FACTURI TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.modifca_persoana TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.cerere_card TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.lichidare_cont_client TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.creare_cont_curent TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.creare_cont_economii TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.creare_cont_depozit TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.lichidare_cont_bancar TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.retrage_fonduri TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.adauga_fonduri TO 'client'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.cerere_transfer TO 'client'@'localhost';
SHOW GRANTS FOR 'client'@'localhost';


GRANT SELECT ON Bancar.* TO 'angajat'@'localhost';
GRANT INSERT ON Bancar.* TO 'angajat'@'localhost';
GRANT UPDATE ON Bancar.* TO 'angajat'@'localhost';
GRANT DELETE ON Bancar.* TO 'angajat'@'localhost';
GRANT SHOW VIEW ON Bancar.* TO 'angajat'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.modifca_persoana TO 'angajat'@'localhost'
GRANT EXECUTE ON PROCEDURE Bancar.Creare_Client TO 'angajat'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.Creare_Angajat TO 'angajat'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.Creare_Admin TO 'angajat'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.aprobare_cerere_card_angajat TO 'angajat'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.aprobare_transfer TO 'angajat'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.lichidare_cont_client TO 'angajat'@'localhost';
SHOW GRANTS FOR 'angajat'@'localhost';

GRANT SELECT ON Bancar.* TO 'admin'@'localhost';
GRANT INSERT ON Bancar.* TO 'admin'@'localhost';
GRANT UPDATE ON Bancar.* TO 'admin'@'localhost';
GRANT DELETE ON Bancar.* TO 'admin'@'localhost';
GRANT SHOW VIEW ON Bancar.* TO 'admin'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.modifca_persoana TO 'admin'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.Creare_Client TO 'admin'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.Creare_Angajat TO 'admin'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.Creare_Admin TO 'admin'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.aprobare_cerere_card_admin TO 'admin'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.aprobare_transfer TO 'admin'@'localhost';
GRANT EXECUTE ON PROCEDURE Bancar.lichidare_cont_client TO 'admin'@'localhost';
SHOW GRANTS FOR 'admin'@'localhost';
