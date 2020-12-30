# AnkiCreator

HOW TO USE
1. Download entire translate folder
2. cd into folder
3. Run javac -d . translate.java Cards/VocabCard.java Cards/ClozerCard.java Cards/InfoCard.java Cards/CardUtils.java Cards/Card.java PeekableScanner.java
4. Copy notes into folder as "notes.md"
5. Run java translate.translate
6. Enjoy! 4 new text files should have been created. One for each of the types: Vocab, Enter, Clozer, and Info. A text file will be created 
even if there were no notes written for tha type.

All scripts must start with START_ANKI and end with STOP_ANKI. 
Example:

START_ANKI

clozer

アメリカにいく{まえ}、３ねん　えいごをべんきょうしていました

Before going to America, I had studied English for 3 years.

If there's a duration don't use まえに, same for あとで。に and で are both for points in time.

---

vocab

セットでコーラとバーガーとフライドポテトをおねがします - How to order a set of cola, burger, and french fries at Mcdonalds.

で is the method! This is the method by which you're ordering, a set as opposed to a single item.

---
STOP_ANKI


Cards are formatted in Anki as below.

**Vocab cards (with Reverse)**

*Front*

Hiragana/katakana + Kanji OR english name


*Back*

Additional Information (Variations of definitions. Godan/Ichidan)

Memorization Hint

Tags


**Enter Card (with Reverse)**

*Front*

Hiragana Or English


*Back*

Additional Information (Variations of definitions. Godan/Ichidan)

Memorization Hint

Tags



**Clozer Card**

*Front*

Example Sentence Hiragana + [Particle + Vocab OR only vocab] Up to Two clozers

Example Sentence English

Example Sentence Kanji

Tags

EX: Lorsqu'il {{c1::test}} demandé qui avait cassé la fenêtre tous les garçons ont pris un air {{c1::innocent}}

/n
**Info Card**

*Front*

Question

*Back*

Answer

Optional additional info/example

Optional Memorizing Hint

Tags
