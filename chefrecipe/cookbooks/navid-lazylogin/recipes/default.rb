#
# Cookbook Name:: navid-lazylogin
# Recipe:: default
#
# Copyright 2014, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

include_recipe "database::mysql"

# Create a mysql database
mysql_database 'lazylogin' do
  connection(
    :host     => node['lazylogin']['datasource']['host'],
    :username => node['lazylogin']['datasource']['username'],
    :password => node['lazylogin']['datasource']['password'] 
  )
  action :create
end


remote_file "/root/springboot.war" do
   source "http://repo.cabotrafalgar.mooo.com/libs-release-local/com/navid/lazylogin/springboot/${project.version}/springboot-${project.version}.war"
end

directory "/root/config" do
  owner "root"
  group "root"
  mode 00644
  action :create
end

template "/root/config/application.properties" do
  mode 0755
  owner "root"
  group "root"
end

# setup the service (based on the script above),
# start it, and make it start at boot
cookbook_file '/etc/init.d/lazylogin' do
    source 'LazyLoginService'
    mode 0755
    owner "root"
    group "root"
end

service "lazylogin" do
    supports :restart => true, :start => true, :stop => true, :reload => true
    action [:enable, :restart]
end
