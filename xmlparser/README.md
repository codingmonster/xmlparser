# xmlparser

This project use dom4j to parse xml files.

There is two way to get detail infos from xml files.

1. you can use xpath to handle Elements & find attributes.

  > you need to import jaxen-1.1-beta-6.jar, or it will cause exceptions (Caused by: java.lang.ClassNotFoundException: org.jaxen.NamespaceContext)

2. you can also creat your own vistor to handle Elements which extends VisitorSupport.
