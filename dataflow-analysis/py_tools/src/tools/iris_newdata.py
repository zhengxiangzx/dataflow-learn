#!/usr/bin/python3
import time
import os
# 用于帮助 textFileStream 产生新数据集

# 训练数据动态生成
trainingDir='E:/github_workspace/dataflow-analysis/spark-streaming/out/production/resources/mllib/training/'
trainingNewDataPath = trainingDir+"iris_libsvm_new.txt"

if os.path.isfile(trainingNewDataPath):
    os.remove(trainingNewDataPath)

trainingData = open(trainingDir+"iris_libsvm_new.txt", "w")

for line in open(trainingDir+"iris_libsvm.txt"):
    trainingData.write(line)
trainingData.close()

# 测试数据生成, 5秒新产生一个文件
testDir='E:/github_workspace/dataflow-analysis/spark-streaming/out/production/resources/mllib/test/'
# 清除文件
def del_file(path):
    ls = os.listdir(path)
    for i in ls:
        c_path = os.path.join(path, i)
        if os.path.isdir(c_path):
            del_file(c_path)
        else:
            os.remove(c_path)

del_file(testDir)
# 产生新文件
def new_file(count):
    testData = open(testDir+"iris_libsvm_"+count+".txt", "w")
    print("testDir create new file:"+testDir+"iris_libsvm_"+count+".txt")
    for line in open(trainingDir+"iris_libsvm_test_input.txt"):
        testData.write(line)
    testData.close()

count = 0
while (count < 11):
    new_file(str(count))
    count = count + 1
    time.sleep(10)












