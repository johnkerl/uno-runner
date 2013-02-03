all: \
	./org/johnkerl/unorunner/UnoCard.class \
	./org/johnkerl/unorunner/UnoCardTest.class \
	./org/johnkerl/unorunner/UnoCardList.class \
	./org/johnkerl/unorunner/UnoDeck.class \
	./org/johnkerl/unorunner/UnoHand.class \
	./org/johnkerl/unorunner/UnoDiscards.class \
	./org/johnkerl/unorunner/UnoHands.class \
	./org/johnkerl/unorunner/UnoGameVisitor.class \
	./org/johnkerl/unorunner/UnoGame.class \
	./org/johnkerl/unorunner/UnoGameRunner.class \
	./org/johnkerl/unorunner/UnoStrategy.class \
	./org/johnkerl/unorunner/SimpleUnoStrategy.class \
	./org/johnkerl/unorunner/SimpleUnoStrategyTest.class

tests: all
	java org.junit.runner.JUnitCore org.johnkerl.unorunner.UnoCardTest
	java org.junit.runner.JUnitCore org.johnkerl.unorunner.SimpleUnoStrategyTest

./org/johnkerl/unorunner/UnoCard.class: ./org/johnkerl/unorunner/UnoCard.java
	javac ./org/johnkerl/unorunner/UnoCard.java
./org/johnkerl/unorunner/UnoCardTest.class: ./org/johnkerl/unorunner/UnoCardTest.java
	javac ./org/johnkerl/unorunner/UnoCardTest.java
./org/johnkerl/unorunner/UnoCardList.class: ./org/johnkerl/unorunner/UnoCardList.java
	javac ./org/johnkerl/unorunner/UnoCardList.java
./org/johnkerl/unorunner/UnoDeck.class: ./org/johnkerl/unorunner/UnoDeck.java
	javac ./org/johnkerl/unorunner/UnoDeck.java
./org/johnkerl/unorunner/UnoHand.class: ./org/johnkerl/unorunner/UnoHand.java
	javac ./org/johnkerl/unorunner/UnoHand.java
./org/johnkerl/unorunner/UnoDiscards.class: ./org/johnkerl/unorunner/UnoDiscards.java
	javac ./org/johnkerl/unorunner/UnoDiscards.java
./org/johnkerl/unorunner/UnoHands.class: ./org/johnkerl/unorunner/UnoHands.java
	javac ./org/johnkerl/unorunner/UnoHands.java
./org/johnkerl/unorunner/UnoGame.class: ./org/johnkerl/unorunner/UnoGame.java
	javac ./org/johnkerl/unorunner/UnoGame.java
./org/johnkerl/unorunner/UnoGameVisitor.class: ./org/johnkerl/unorunner/UnoGameVisitor.java
	javac ./org/johnkerl/unorunner/UnoGameVisitor.java
./org/johnkerl/unorunner/UnoGameRunner.class: ./org/johnkerl/unorunner/UnoGameRunner.java
	javac ./org/johnkerl/unorunner/UnoGameRunner.java
./org/johnkerl/unorunner/UnoStrategy.class: ./org/johnkerl/unorunner/UnoStrategy.java
	javac ./org/johnkerl/unorunner/UnoStrategy.java
./org/johnkerl/unorunner/SimpleUnoStrategy.class: ./org/johnkerl/unorunner/SimpleUnoStrategy.java
	javac ./org/johnkerl/unorunner/SimpleUnoStrategy.java
./org/johnkerl/unorunner/SimpleUnoStrategyTest.class: ./org/johnkerl/unorunner/SimpleUnoStrategyTest.java
	javac ./org/johnkerl/unorunner/SimpleUnoStrategyTest.java

clean:
	@-find . -name '*.class' -exec rm -vf {} \;
