# ReComment Blog
# Python 正则匹配代码注释
```python
    s="aaaaaaa/*afdshdafhda*/hhhhhhhhhh/*afdshdafhda*/ // data2 2.5f"
    m = re.compile(r'//.*')
    outtmp = re.sub(m, ' ', s)
    m = re.compile(r'/\*.*?\*/', re.S)
    result = re.sub(m, ' ', outtmp)
```
resut:aaaaaaa hhhhhhhhhh
