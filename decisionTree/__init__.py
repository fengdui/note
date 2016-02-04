import trees

myDat, labels = trees.createDataSet()
print(myDat)
print(labels)
print(trees.calcShannonEnt(myDat))

print(trees.splitDataSet(myDat, 0, 0))

print(trees.createTree(myDat, labels))