SELECT * FROM persoana JOIN client ON (persoana.CNP = client.CNP) ;  -- toti clientii
CALL Creare_Admin('Haurentiu', 'Mircel', '0000', 'str. Constanta', '0966874361', 'H.Mircel@yahoo.com', 'contact', '8', 100, 3500, 'HR', 'CLUJ_NAPOCA');
CALL Creare_Client('Pop', 'Vasile', '0000', 'str. Horea', '0766874361', 'vasilikaPopika@gmail.com', 'tinta in vizor', '1', '1946-12-20', 'salariat', 1); 
CALL Creare_Client('Gigi', 'Aurel', '0000', 'str. Baciu', '0766874123', 'F.Aurel@yahoo.com', 'contact', '2', '1946-12-19', 'umflat baloane', 1);  
CALL Creare_Client('Nicolae', 'Ceausescu', '0000', 'str. 9 mai', '0712374361', 'diodio@gmail.com', 'asedfghjk', '3', '1946-12-20', 'vanzare de culegeri', 1);
CALL creare_cont_curent('BRD', '1');  -- obligatoriu cate un cont curent
CALL creare_cont_curent('ING', '2');
CALL creare_cont_curent('ING', '3');
CALL Creare_Angajat('Unguru', 'Bubi', '0000', 'str. Peana', '0766874261', 'U.B@gmail.com', 'contact', '10', 150, 3000, 'FUNCTIONAR', 'CLUJ_NAPOCA');
INSERT INTO CLIENT VALUES(current_date(),'angajat la banca','0','1',10);
call creare_cont_curent('BTRL', 10);
CALL Creare_Angajat('Ionescu', 'Alexandrescu', '0000', 'str. Peana', '0766874261', 'U.B@gmail.com', 'contact', '20', 150, 3000, 'HR', 'CLUJ_NAPOCA');
INSERT INTO CLIENT VALUES(current_date(),'angajat la banca','0','1',20);
call creare_cont_curent('BTRL', 20);