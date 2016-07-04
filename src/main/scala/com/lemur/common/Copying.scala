package com.lemur.common

/**
  * Created by lemur on 03/07/16.
  */
trait Copying[T] {
  def copyWith(changes: (String, AnyRef)*): T = {
    val clazz = getClass
    val constructor = clazz.getDeclaredConstructors.head
    val argumentCount = constructor.getParameterTypes.size
    val fields = clazz.getDeclaredFields
    val arguments = (0 until argumentCount) map { i =>
      val fieldName = fields(i).getName
      changes.find(_._1 == fieldName) match {
        case Some(change) => change._2
        case None => {
          val getter = clazz.getMethod(fieldName)
          getter.invoke(this)
        }
      }

    }
    constructor.newInstance(arguments: _*).asInstanceOf[T]
  }
}
