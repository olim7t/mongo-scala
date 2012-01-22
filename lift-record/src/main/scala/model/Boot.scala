package model

import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.mongodb.Mongo


/** Bootstrap class to initialize the MongoDB connection */
object Boot {
  def defineDB {
    // http://www.assembla.com/wiki/show/liftweb/Mongo_Configuration
    MongoDB.defineDb(DefaultMongoIdentifier, new Mongo, "WhatToDo")
  }
}