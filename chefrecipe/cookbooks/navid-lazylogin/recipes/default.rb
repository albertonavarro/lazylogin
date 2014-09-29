#
# Cookbook Name:: navid-lazylogin
# Recipe:: default
#
# Copyright 2014, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

remote_file "/root/springboot.war" do
   source "http://repo.cabotrafalgar.mooo.com/libs-release-local/com/navid/lazylogin/springboot/${project.version}/springboot-${project.version}.war"
end

directory "/root/navidconfig" do
  owner "root"
  group "root"
  mode 00644
  action :create
end

template "/root/navidconfig/lazylogin.overrides" do
  mode 0755
  owner "root"
  group "root"
end

# script file used by service to launch your java program
file "/root/run_lazylogin.cmd" do
    content "java -jar /root/jetty-deployable.jar\n"
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
