# WalkMyPet
O aplicatie ce le permite detinatorilor de animale de companie sa gaseasca pe cineva sa le scoata la plimbare.
2 categorii de utilizatori:
- Prestatorii de serviciu - persoane ce in urma unei cereri de inregistrare devin disponibile sa plimbe animalele de companie ale clientilor
- Clientii- persoane ce detin un animal de companie si in urma inregistrarii pe platforma oricand au nevoie pot cauta o persoana disponibila sa le plimbe animalul de companie

Flow-uri
1. Register
2. Login
3. Plasare comanda
4. Confirmare
5. Refuzare comanda client
6. Oferire feedback
7. Platire servicii cash
8. Editare profil user
9. Trimitere factura pe e-mail

Domeniu
1. Client (nume, prenume, lista de animale de companie, adresa)
2. Animal de companie (nume, rasa, descriere, kg)
3. Prestator servicii (nume,adresa)
4. Adresa (localitate, strada, etc...)
6. Profil (username, adresa de mail, parola, poza, etc...)
7. Comanda (interval orar, cine face comanda, cine preia comanda, status)
status:
OPEN
BOOKED 
IN_PROGRESS 
FINISHED
CANCELLED (cand oricare dintre client sau prestator anuleaza comanda)
8. Plata (suma, platitor, beneficiar, cash)
9. Feedback
