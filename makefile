JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        jobscheduler.jobscheduler.java \
        jobscheduler.jobscheduler.Scheduler.java \
        jobscheduler.jobscheduler.Job.java \
	jobscheduler.jobscheduler.MinHeap.java \
	jobscheduler.jobscheduler.RBTree.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class