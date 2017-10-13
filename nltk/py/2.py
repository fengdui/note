# from nltk.corpus import gutenberg
# nltk.download()
# w = gutenberg.fileids()
# print(w)

# print(len(gutenberg.words('austen-emma.txt')))

# for field in gutenberg.fileids():
#     print(field)
#     num_char = len(gutenberg.raw(field))
#     num_words = len(gutenberg.words(field))
#     num_sents = len(gutenberg.sents(field))
#     print(num_char, num_words, num_sents)

from nltk.corpus import brown
import nltk
# for genre in brown.categories():
#     print(genre)
gener_word = [(genre, word) for genre in ['news', 'romance'] for word in brown.words(categories=genre)]
# print(gener_word)

cfd = nltk.ConditionalFreqDist(gener_word)
print(cfd)
print(cfd.conditions())