package models

trait Configuration {

  def mongoHost: String

  def mongoPort: Int

  def mongoDbName: String
}
