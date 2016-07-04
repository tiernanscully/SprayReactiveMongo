package com.lemur.models

import com.wordnik.swagger.annotations.{ApiModel, ApiModelProperty}

import scala.annotation.meta.field

@ApiModel(description = "A common message object")
case class CommonMessageModel(
                               @(ApiModelProperty@field)(value = "message")
                               val message: String)
