#Ce script n'est pas vraiment un script d'install mais son execution devrait suffire
#Attention il efface tout le rep webapps
#on a pas réussit a configurer comme on voulais le elasticsearch en passant par /etc/init.d/elasticsearch donc on le fait à la main
#le problème c'est que plusieur instance peuvent etre créer si il ya une répétitition du script... ps -ef & kill est la seul solution que l'on a trouvé
#Du faite que les test de notre appli remplisse la base de donnée leur deuxième execution créer des erreurs et empeche la war de ce créer
#Ces test d'integration son effectuer uniquement lors de la configuration
/etc/init.d/mongodb stop
/etc/init.d/mongodb start
/etc/init.d/elasticsearch stop
/usr/share/elasticsearch/bin/elasticsearch -f -D es.config=/usr/share/elasticsearch/config/elasticsearch.yml -Des.index.store.type=memory &
mvn package -DskipTests
/etc/init.d/tomcat7 stop
mkdir -p /var/lib/tomcat7/webapps/*
rm -rvf /var/lib/tomcat7/webapps/windmeal.war
rm -rvf /var/lib/tomcat7/webapps/*
cp -rf target/windmeal-1.0-SNAPSHOT.war  /var/lib/tomcat7/webapps/windmeal.war
/etc/init.d/tomcat7 start