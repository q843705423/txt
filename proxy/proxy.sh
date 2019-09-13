firewall-cmd --add-port=13104/tcp --permanent
firewall-cmd --reload

yum install java-1.8.0-openjdk* -y

yum -y install git
cd /root
git clone https://github.com/q843705423/txt

nohup java -jar /root/txt/proxy/proxyspider-0.0.1-SNAPSHOT.jar > /root/txt/proxy/log &

mkdir /root/.ssh

echo 
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDlkT+YwRzakotehMgnV0wWpBtr539jseYKgwHEzxqL6cLTsHAqz8hY+JVDgx7osr1JFxwnH9nMfpIwbSIG/qDRGJT7rDW+GYhRRH6mAVNm7pR0wHK/+ZFEhUIPad9gskEjXt9VpMWY6yCab6D6PgxW6TpGQUgIhHPInL3th6CD7CqKjkeIKu8GNybycZWUue/n1CS6FurFVDHLf44zt1BCWC1CO1M8d2/C9Yec/ek3prgnddtCHy/qbNCluvIyAQrr0UBfSCUBlSufsBoeVBpoVTRYRP5tQ4NYhHZVf1IA04OLVlPdyIO1bSKIxQfNomyMgaMgGTr3JsCFqy+ZW8AJ qiqi.chen@teradata.com > /root/.ssh/authorized_keys


