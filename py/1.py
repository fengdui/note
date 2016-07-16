import nltk
# nltk.download()
from nltk.book import *

# text1.concordance("monstrous")
# 查询单词出现上下文
# text1.similar("monstrous")
# 查询单词出现在相似的上下文

# text2.common_contexts(["monstrous", "very"])
# 函数common_contexts允许我们研究两个或两个以上的词共同的上下文

# text3.generate()

# print(len(text3))
# print(text3.count("smote"))

# print(text1[:])

# fdist1 = FreqDist(text1)
# 得到单词出现频率
# print(fdist1)

# vocabulary = fdist1.keys()
# print(vocabulary[:50])

# print(fdist1["whale"])

# print(fdist1.hapaxes())
# 得到低频词

# V = set(text1)
# long_word = [w for w in V if len(w) > 15]
# print(long_word)
# print(nltk.bigrams(['more', 'is', 'said', 'than', 'done']))
# 双连词

# text4.collocations()

# fdist = FreqDist([len(w) for w in text1])
# print(fdist.keys())
# print(fdist.freq(3))