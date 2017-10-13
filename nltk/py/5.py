# import nltk
#
# text = nltk.word_tokenize("what you name")
# print(text)
#
# w = nltk.pos_tag(text)
# print(w)
#
# tagged_token = nltk.tag.str2tuple('fly/NN')
#
# print(tagged_token)


from nltk.corpus import brown

w = brown.tagged_words()
print(w)