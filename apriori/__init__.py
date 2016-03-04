
import apriori

dataMat = apriori.loadDataSet()
print(dataMat)

dataSet = apriori.createC1(dataMat)
print(dataSet)

L, supportData = apriori.apriori(dataMat)
print(L)
print(supportData)

apriori.generateRules(L, supportData, 0.5)