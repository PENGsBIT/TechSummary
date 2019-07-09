if __name__ == '__main__':
    list = [3, 5, 2, 6, 7]
    for index, i in enumerate(list):
        print("%d,%s" % (index, i))
    a = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    a[1:6:-1]
    #  print[]
    # step=-1，决定了从右往左取值，而start_index=1到end_index=6决定了从左往右取值，两者矛盾
    a[2+1:3*2:7%3]
    # >>> [3, 4, 5]
    # 即：a[2+1:3*2:7%3] = a[3:6:1]
