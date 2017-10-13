import kMeans
from numpy import *
dataMat = mat(kMeans.loadDataSet('testSet.txt'))
# print dataMat

randMat = kMeans.randCent(dataMat, 2)
# print dataMat[:, 0]
# print randMat

res = kMeans.kMeans(dataMat, 4)
# print res

dataMat3 = mat(kMeans.loadDataSet('testSet2.txt'))
kMeans.biKmeans(dataMat3, 3)

# centList, myNewAssments =