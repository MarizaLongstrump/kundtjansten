1.Hur konflikt med portnummer har löst.  

När två eller flera personer gör en förändring i samma fil och i samma rad.  
Om förändring skilja sig från varandra.  
Alla personer gör en commit och pusha kod.  
Så uppstår en kod konflikt.    
Eftersom kod inte är samma man kan se att kod blev inte längre homogeen.   
Det går inte att merge.  
Man måste först lösa konklikt igenom att kommer övereens vilken kod behållas vilken tas bort om det behovs.  
I ditt fall var konflikt pga vi hade olika portnummer.    
Så vi kom överens vilken portnummer som var den rätta.    
Vi rättade portnummer.  
Så markerar man som resolved.  
klicka på: commit merg.  
Klicka på: Merge pull request.  
Man får ett commit meddelande.   
Man kan skriva ett meddelande i extended description: typ vad man valde för lösning för problem.  
Klicka på: confirm merge.  
Men kod ska inte bli automatisk förändrade i mitt lokalt gitrepository.   
Det betyder att om man går tillbaka i sin lokal program ska man se att kod är oförändrade där.  
Senare när kod  blir godkänd i github ska man går till sin dator och gör så här.  
Byta till maste branch.  
Klicka på update projekt för att hämta förändringar till gitrepository.   

2. Hela flöde
3. Jag arbetar i till exempel i kundtjänsten
4. Skapar en branch som heter feature-logging.
5. Skriver kod som behövs för att logging fungerar.
6. Testar
7. Commit
8. Skapar PR
9. PR aproved
10. Merge till main
11. CI körs
12. CD körs
13. Allt blir deployade i staging
14. Testar deployade i teste mijlö
15. Godkände
16. Sync till production
17. Kund kan använda. 

