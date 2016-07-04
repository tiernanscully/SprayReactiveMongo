package com.lemur.database

/**
  * Created by lemur on 03/07/16.
  */
abstract sealed class DatabaseException(exception: Exception) extends Exception

case class DatabaseInsertionException(exception: Exception) extends DatabaseException(exception)
case class DatabaseFindException(exception: Exception) extends DatabaseException(exception)
case class DatabaseUpdateException(exception: Exception) extends DatabaseException(exception)
case class DatabaseDeletionException(exception: Exception) extends DatabaseException(exception)