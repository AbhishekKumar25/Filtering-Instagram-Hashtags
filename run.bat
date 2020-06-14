set classpath=lib/miglayout-3.6-swing.jar;lib/mysql-connector-java-5.1.6-bin.jar;lib/jgrapht.jar;lib/jgraph.jar;.;
javac -d . *.java
java -Xmx1000M com.Login
pause