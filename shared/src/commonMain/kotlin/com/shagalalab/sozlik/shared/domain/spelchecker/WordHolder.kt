package com.shagalalab.sozlik.shared.domain.spelchecker

import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary

object WordHolder {
    private val wordHashMap: MutableMap<String, Long> = mutableMapOf()
    val wordMap: Map<String, Long>
        get() = wordHashMap

    fun setWordMap(wordList: List<Dictionary>) {
        for (w in wordList) {
            wordHashMap[w.word] = w.id
        }
    }
}
