# Internet shop

## Table of Contents
* [Description](#description)
* [Introduction - the project's aim](#aim)
* [Technologies](#technologies)
* [Launch](#launch)

## <a name="description"></a>Description
Web application, where user can enter the shop, look through available products, add products to bucket and then create orders with chosen products. Admin can manage users list and products list.

## <a name="aim"></a>Introduction - the project's aim

This is a training project. It's aim is to improve my programming skills, deepen knowledge in hibernate technology.

## <a name="technologies"></a>Technologies

* Java 11
* Maven 4.0
* Servlet 4.0.1
* Hibernate 5.4.5
* MySQL database 

## <a name="launch"></a>Launch

1. Clone or download the project from github.com
2. Add to IDE as maven project
3. Add tomcat configuration
4. Add artifact internetshop:war exploded
5. Run all queries from init_db.sql file
6. Change hibernate.cfg.xml file, set your login and password
7. Change Factory.class, set your login and password in DriverManager.getConnection() method
8. Change log4j.properties, set new link for internetshop.log file in your computer
