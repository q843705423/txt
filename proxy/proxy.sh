firewall-cmd --add-port=13104/tcp --permanent
firewall-cmd --reload

yum install java-1.8.0-openjdk* -y

git yum -y install git
cd /root
git clone https://github.com/q843705423/txt

nohup java -jar /root/txt/proxy/proxyspider-0.0.1-SNAPSHOT.jar > /root/txt/proxy/log &