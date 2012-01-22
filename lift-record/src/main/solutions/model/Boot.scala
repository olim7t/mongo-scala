package model

import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.mongodb.Mongo


/** Bootstrap class to initialize the MongoDB connection */
object Boot {
  def defineDB {
    MongoDB.defineDb(DefaultMongoIdentifier, new Mongo, "WhatToDo")
  }
}