import fpGrowth

simpDat = fpGrowth.loadSimpDat()
# print simpDat

initSet = fpGrowth.createInitSet(simpDat)
# print initSet

myFpTree, myHeaderTab = fpGrowth.createTree(initSet, 3)
# print myFpTree.disp()
# print myHeaderTab

myCondPat = fpGrowth.findPrefixPath('x', myHeaderTab['x'][1])
# print myCondPat

freqItems = []

myfpGrowth = fpGrowth.mineTree(myFpTree, myHeaderTab, 3, set([]), freqItems)
# print myFpTree.disp()