name             'navid-lazylogin'
maintainer       'YOUR_COMPANY_NAME'
maintainer_email 'YOUR_EMAIL'
license          'All rights reserved'
description      'Installs/Configures navid-lazylogin'
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          '${project.version}'

%w{ java simple_iptables zabbix }.each do |cb|
  depends cb
end