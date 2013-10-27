Dépo nécessaire
==
Pour installer cette webapp il faut
Java sdk jre 7
maven
Tomcat 6 ou 7
elasticsearch 0.90.5
mongodb 2.2 (le fichier de conf ne marche que pour les mongodb anterieur à la version 2.2)
En effet la syntaxe pour ajouter un utilisateur n'est pas la même dans les versions supérieurs

Installation sur une debian avec les script beta
==
il faut configurer elasticsearch sur les ports standards (9300 et 9200) avec les fichiers de conf dans config_elasticsearch
Les 3 scripts devraient faire fonctionner la webapp sur une debian
Après avoir cloner le repo dans le dossier /!\ /home/progweb/windmeal/ (git clone https://github.com/DDeis/Windmeal.git)
il faut  faire :

cd Windmeal
chmod +x *.sh
./install.sh #Install les different repo utilisé
./configure.sh #Configure elasticsearch et mongodb et fill la database grace au test (depreciated)
./run.sh

l'application après le temps nécéssaire au déploiement devrait etre disponible à localhost:8080/windmeal/

Indentification d'érreur
==
Aide à l'identification d'erreur :
Si l'addresse localhost:8080/windmeal/ ne répond pas regarder les logs de tomcat (erreur probable tomcat a démarré avec java 1.6 ou mongodb pas lancé)
Si la barre de recherche ne marche pas ouvrir une console javascript sur le browser et si erreur 500 dans les connection c'est elasticsearch qui est mal configuré (verfier que la configuration a bien été prise en compte et que elasticsearch est sur les ports standards)


Pour toute erreur merci de me contacter à labusquiere@et.esiea.fr