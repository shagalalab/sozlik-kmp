package com.shagalalab.sozlik.shared.domain.spelchecker

import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository

class SpellChecker(private val words: Map<String, Long> = emptyMap(), private val repository: DictionaryRepository) {

    suspend fun check(word: String, isLatin: Boolean): List<Dictionary> {
        val alphabet = if (isLatin) ALPHABET_LATIN else ALPHABET_CYRILLIC
        val alternatives: MutableList<Dictionary> = mutableListOf()
        var model: Dictionary?
        if (words.containsKey(word)) {
            model = repository.getTranslation(word).getOrNull()
            if (model != null) {
                alternatives.add(model)
            }
            return alternatives
        }
        val edits = edits1(word, alphabet)
        for (w in edits) {
            if (words.containsKey(w)) {
                model = repository.getTranslation(w).getOrNull()
                if (model != null) {
                    alternatives.add(model)
                }
            }
        }
        return alternatives.sortedWith { a, b -> a.word.compareTo(b.word) }
    }

    companion object {
        const val ALPHABET_LATIN = "aábcdefgǵhiıjklmnńoópqrstuúvwxyz"
        const val ALPHABET_CYRILLIC = "аәбвгғдеёжзийкқлмнңоөпрстуүўфхҳцчшщъыьэюя"
        private fun edits1(word: String, alphabet: String): Set<String> {
            val edits: MutableSet<String> = mutableSetOf()
            val splits: List<SplitWord> = SplitWord.allSplits(word)
            for (split in splits) {
                val a: String = split.prefix
                val b: String = split.suffix
                val lb = b.length
                if (lb > 0) {
                    edits.add(a + b.substring(1)) // delete 1 character
                    for (element in alphabet) {
                        edits.add(a + element + b.substring(1)) // replace 1 characters
                    }
                }
                if (lb > 1) {
                    edits.add(a + b.substring(2)) //delete 2 characters
                    edits.add(a + b[1] + b[0] + b.substring(2)) // transpose
                    for (element1 in alphabet) {
                        for (element2 in alphabet) {
                            edits.add(a + element1 + element2 + b.substring(2)) // replace 2 characters
                        }
                    }
                }
                for (i in alphabet.indices) {
                    edits.add(a + alphabet[i] + b) // insert
                    for (element in alphabet) {
                        edits.add(a + alphabet[i] + element + b)
                    }
                }
            }
            return edits
        }
    }
}
