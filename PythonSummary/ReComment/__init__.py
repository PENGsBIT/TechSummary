import re

if __name__ == '__main__':
    s = "afas/*afdshdafhda*/hasdfas/*afdshdafhda*/ // data2 2.5f"
    m = re.compile(r'//.*')
    outtmp = re.sub(m, ' ', s)
    m = re.compile(r'/\*.*?\*/', re.S)
    result = re.sub(m, ' ', outtmp)
    print(result)
