#the mvn package fill the database with test if u want to reexecute it add -DskipTests
#Ce fichier oblige tomcat et maven a utiliser la version 7 de java et configure elasticsearch
#mongodb et elasticsearch doit fonctionner pour que ce fichier marche
#COnfigure la database (enable geloacation and create an user)
export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-i386/
mongo --shell < mongo_conf.js
mkdir -p /usr/share/elasticsearch/config
sudo cp -r config_elasticsearch/* /usr/share/elasticsearch/config
mv config_elasticsearch/ /usr/share/elasticsearch/config
/usr/share/elasticsearch/bin/elasticsearch -f -D es.config=/usr/share/elasticsearch/config/elasticsearch.yml -Des.index.store.type=memory &
mvn package