# Discover MongoDB with Scala

This is the material for a workshop I'm giving at [Xebia FR](http://www.xebia.fr/) on February 2nd, 2012. I present various libraries to access MongoDB from Scala (namely: Casbah, Salat, Lift Record and Rogue)

Here's what you'll find in the subdirectories:

* `prez`: my 1-hour presentation (made with the awesome [ShowOff](https://github.com/schacon/showoff)). There are few slides, as I will spend most of the time demonstrating stuff in the Scala REPL ; a tentative script of what I'll say is available in `demo/demo.md`.
* `demo`: the SBT project of my demonstration. Used to get a REPL with the relevant libs in my classpath, and to prepare stuff that I consider too boring/too long to type live.
* `salat` and `lift-record`: two workshops with various exercises. These are not part of my presentation, but provided to give an opportunity to practice from home.

## Workshop setup

### Software
The bare minimum is [MongoDB](http://www.mongodb.org/downloads) (duh!), a Java VM, and [SBT](https://github.com/harrah/xsbt/wiki/Getting-Started-Setup).

### Install the test database
* start your MongoDB daemon (`mongod`);
* get [`WhatToDo.zip`](https://github.com/karesti/Hands-On-Mongo-Java/raw/master/dumpMongo/WhatToDo.zip) from Katia's repo;
* unzip the archive and locate the file `events.bson`;
* run the following command:

        mongorestore -d WhatToDo events.bson

### Solve the problems
For each workshop, `src/test/scala` contains failing unit tests (run them with `sbt test` or from your IDE). You have to fix the sources in `src/main/scala` to make the tests pass. `src/main/solutions` contains the corrected code.
