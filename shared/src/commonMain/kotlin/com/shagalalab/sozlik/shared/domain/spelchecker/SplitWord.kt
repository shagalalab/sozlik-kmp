package com.shagalalab.sozlik.shared.domain.spelchecker

class SplitWord private constructor(word: String, splitPos: Int) {
    val prefix: String
    val suffix: String

    init {
        prefix = word.substring(0, splitPos)
        suffix = word.substring(splitPos, word.length)
    }

    override fun toString(): String {
        return "$prefix-$suffix"
    }

    companion object {
        fun allSplits(word: String): List<SplitWord> {
            val splits: MutableList<SplitWord> = mutableListOf()
            for (i in 0..word.length) {
                splits.add(SplitWord(word, i))
            }
            return splits
        }
    }
}
