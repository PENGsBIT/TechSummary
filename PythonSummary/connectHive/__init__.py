# coding=UTF-8
import numpy as np
import pandas as pd
import env
import MySQLdb
from normalized import *
from classification import *

if __name__ == '__main__':
    connect = MySQLdb.Connect(host=env.host, port=env.port, user=env.user, passwd=env.password, db=env.db)
    cur = connect.cursor()
    cur.execute("SELECT download,view,size,subsets,created,updated FROM " + env.db + '.' + env.tableName)
    data = []
    data = np.array(cur.fetchall())
    cur.close()
    colName = np.array(["download", "view", "size", "subsets", "created", "updated"])
    decision = 'lin'
    # target = data[:, 0]
    # structuredData = data[:, 1: 4]
    # timeData =data[:, [4, 5]]
    # target = formatTarget(data[:, 0])
    # structuredData = formatStructuredData(data[:, 1: 4])
    # timeData = formatTime(data[:, [4, 5]])
    # formatData = np.concatenate((structuredData, timeData), axis=1)
    # if decision == 'dt':
    #     skDecisionTreeTraining(data, formatData, structuredData, timeData, target, colName)
    # elif decision == 'dnn':
    #     tfDNNClassiferTraining(data, formatData, structuredData, timeData, target, colName)
    # elif decision == 'lin':
    #     tfLinearClassifierTraing(data, formatData, structuredData, timeData, target, colName)

    data = list(cur.fetchall())
    data=pd.DataFrame(data)
    print(data.isnull().sum())
    cur.execute(" DESC " + env.db + '.' + env.tableName)
    description = cur.fetchall()
    print(description)
