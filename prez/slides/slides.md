!SLIDE center

![MongoDB](mongodb.png)
#+#
![Scala](scala.png)

!SLIDE incremental center
![menu](special.jpg)

* showcase various options to access MongoDB from Scala
* live coding
* more material available at [github.com/olim7t/mongo-scala](http://github.com/olim7t/mongo-scala)

<span style="height: 0.5em; color: lightgray;">http://www.flickr.com/photos/mikefischer/4267908338/</span>

!SLIDE
    @@@scala
    object OlivierMichallat {

      val company = "Xebia"

      val blog = "blog.xebia.fr" +
                 "/author/omichallat"

      val twitter = "@olim7t"

      val github = "github.com/olim7t"
    }

!SLIDE center
# Libraries overview

!SLIDE center
![layers](layers1.png)

**mongo-java-driver**

!SLIDE center
![layers](layers2.png)

**Casbah**: toolkit for better integration with Scala

!SLIDE center
![layers](layers3.png)

**Salat**: case class ↔ MongoDB mapping

!SLIDE center
![layers](layers4.png)

**lift-mongodb-record**: Lift's persistence layer

!SLIDE center
![layers](layers5.png)

**Rogue**: type-safe query DSL

!SLIDE center
#Demo


!SLIDE center
#Conclusion

!SLIDE center incremental
#Casbah

* flexible
* weakly typed


!SLIDE center incremental
# Salat

* strongly typed domain objects
* case classes promote functional style
* less forgiving of unexpected document structures


!SLIDE center incremental
# Lift Record

* good integration with Lift
* typesafe requests with Rogue
* a bit invasive (classes extend framework's types)
