JUNIT_JAR = /usr/share/java/junit4.jar

compile:
	javac -Xlint:all -cp .:$(JUNIT_JAR) *.java

test: compile
	java -ea -cp .:$(JUNIT_JAR) org.junit.runner.JUnitCore GraphTest

%.pdf: %.dot
	neato $< -Tpdf >$@

clean:
	rm -f *.class $(patsubst %.dot,%.pdf,$(wildcard *.dot))

.PHONY: compile test clean
