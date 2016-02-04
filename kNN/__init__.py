import kNN
import scipy
# import matplotlib
# import matplotlib.pyplot as plt

datingDataMat, datingLabels = kNN.file2matrix('datingTestSet2.txt')
print(datingDataMat)
print(datingLabels)

# fig = plt.figure()
# ax = fig.add_subplot(111)
# ax.scatter(datingDataMat[:, 1], datingDataMat[:, 2])
# plt.show()

normMat, ranges, minVals = kNN.autoNorm(datingDataMat)
print(normMat)

kNN.datingClassTest()
kNN.handwritingClassTest()