sudo apt-get update
sudo apt-get install git
sudo apt-get install openjdk-7-jre
sudo apt-get install openjdk-7-jdk
sudo apt-get install maven

sudo apt-key adv --keyserver keyserver.ubuntu.com --recv 7F0CEB10
deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen
sudo apt-get update 

sudo apt-get install mongodb-10gen
sudo apt-get install tomcat7
wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-0.90.5.deb
sudo dpkg -i elasticsearch-0.90.5.deb
