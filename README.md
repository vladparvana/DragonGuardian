# DragonGuardian
2D Game in Java
Proiectarea contextului și descriere detaliată
Actiunea jocului este proiectata intr-o lumea straveche, plina de magie si mistere, in regatul “Taramul Dragonului”. Acest regat era cunoscut pentru legendarul său protector, un erou înnăscut în familia "Stăpânului Sângelui", ai cărui descendenți purtau numele de "Dragon Guardian".
Cu mulți ani în urmă, când Dragon Guardian era doar un copil, regatul a fost supus unei trădări crunte. O familie rivală, cunoscută sub numele de "Familia Întunecată", a preluat tronul și a luat controlul asupra regatului. În acel moment tragic, Dragon Guardian a fost nevoit să-și părăsească căminul și să fugă în exil pentru a-și păstra viața.
Cu inima plină de hotărâre și setea de răzbunare, Dragon Guardian a pornit spre regatul său natal pentru a recupera tronul și pentru a elibera dragonul captiv. Calea lui era plină de primejdii, dar el știa că destinul său era să devină un adevărat erou și să-și razbune stramosii.

Proiectare sistem si descriere detaliata
Jocul este creat in stil PixelArt, cu camera centrata pe erou, iar harta este generata doar in campul vizual al acestuia.
Miscare
Eroul se afla in drumul sau spre regatul “Taramul Dragonului” sa-si schimbe destinul. Caracterul se poate deplasa de pe tastele W(in sus), A(in stanga), S(in jos) si D(in dreapta).
Actiune
In drumul sau, eroul va interactiona cu lupi, soldati si arcasi ai familiei “Stapanului Sangelui”, acesta putand sa-i evite sau sa se dueleze cu ei, castigand astfel puncte. Cand jucatorul apasa tasta SPACE, eroul va da o lovitura de sabie, lovind inamicii din jurul acestuia. Incepand cu nivelul 2, acesta va avea access la un arc pentru a-l ajuta in lupta cu familia tradatoare, putand fi folosit apasand tasta G.
HealthPoints(HP)
Eroul are la inceput 10 puncte de viata, iar cand acesta va fi atacat de un inamic, viata acestuia v-a scadea cu 2 puncte. Punctele de viata pot creste sau scadea prin acumularea obiectelor intalnite pe drumul sau catre regat.
Reguli
Regulile principale ale jocului sunt :
-	Obtinerea punctelor necesare pentru a trece la nivelul urmator 
-	Eliberarea ultimului dragon existent din capitivitatea tradatorilor
Jucatorul moare atunci cand punctele de viata ajung la 0.
Proiectarea nivelurilor și descriere detaliată.
Exista 3 nivele si toate sunt generate in urma incarcarii a 3 matrici de dimensiuni 30x30,50x50 si 50x50, si valori(asociate cu anumite tile-uri) diferite incarcate din 3 fisiere de tip “.txt”. De la un nivel la altul, eroul isi pastreaza scorul si viata.
Nivelul 1: Eroul isi incepe adventura in padurea ce inconjoara regatul si are de infruntat in drumul sau haitele de lupi ce pazesc padurea. Datorita lupilor, padurea este un loc sigur de a depozita comorile, eroul putand explora harta si a deschide cufere ascunse in padure.
Nivelul 2: Jocul devine mai complitat pe parcurs ce eroul se aproprie de regat, intalnind in drumul sau razboinici ai armatei care vor sa il omoare cu orice pret. Totusi, eroul incepe sa aiba access la un arc pentru a-l ajuta in obtinerea scopul sau.
Nivelul 3: este ultimul nivel si cel mai greu, acesta desfasurandu-se in interiorul regatului. Eroul va trebuie sa se lupte cu razboinicii si arcasii regatului pentru a dobandi destule puncte pentru a deschide poarta catre insula in care se afla ultimul dragon existent.
