package com.hubelias.bigdata.crawler.relations

data class RelationElement(
        val time: String?,
        val message: String) {
    companion object {
        fun get(time: String?, message: String?) : RelationElement {
            val trimmedTime = time?.trim()
            val cleanedTime = trimmedTime?.let { if(it.isBlank()) null else it }
            val trimmedMessage = message?.trim() ?: ""

            return RelationElement(cleanedTime, trimmedMessage)
        }
    }
}