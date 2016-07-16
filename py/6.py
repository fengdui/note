import nltk
# nltk.download()

from nltk.corpus import names
import random

def gender_features(word):
    return {'last_letter': word[-1]}
names = ([(name, 'male') for name in names.words('male.txt')] + [(name, 'female') for name in names.words('female.txt')])
# print(names)

random.shuffle(names)
featureset = [(gender_features(n), g) for (n, g) in names]

# print(featureset)
train_set, test_set = featureset[500:], featureset[:500]
classifier = nltk.NaiveBayesClassifier.train(train_set)
# print(classifier)

# print(classifier.classify(gender_features("Neo")))

# print(nltk.classify.accuracy(classifier, test_set))
# classifier.show_most_informative_features(5)

from nltk.classify import apply_features
train_set = apply_features(gender_features(), featureset[500:])