# Python3 链接Hive填坑
Python2使用impyla轻松链接Hive，Python3各种错误

## install
```bash
pip install six
pip install bit_array
pip install thriftpy  (##注意: thrift (on Python 2.x) or thriftpy (on Python 3.x))
pip install thrift_sasl 
pip install sasl
```

## error1
```bash
'TSocket' object has no attribute 'isOpen'
```
控制 thrift_sasl 版本0.2.1 

## error2
```bash
thriftpy.transport.TTransportException: TTransportException(message="Could not start SASL: b'Error in sasl_client_start (-4) SASL(-4): no mechanism available: Unable to find a callback: 2'", type=1)
```
其中，authMechanism的值取决于hive-site.xml里的配置  
```xml
<name>hive.server2.authentication</name>  
<value>NOSASL</value>  
```
auth_mechanism 这个参数的取值可以是：’NOSASL’, ‘PLAIN’, ‘KERBEROS’, ‘LDAP’.  
connect加上参数 “auth_mechanism”
```python
conn = connect(host='*',port = 10000,auth_mechanism='NONE')
```
## error3
```bash
ThriftParserError: ThriftPy does not support generating module with path in protocol ‘c’
```
定位到 \Lib\site-packages\thriftpy\parser\parser.py
```python
if url_scheme == '':
    with open(path) as fh:
        data = fh.read()
elif url_scheme in ('http', 'https'):
    data = urlopen(path).read()
else:
    raise ThriftParserError('ThriftPy does not support generating module '
                            'with path in protocol \'{}\''.format(
                                url_scheme))
```
修改为
```python
if url_scheme == '':
    with open(path) as fh:
        data = fh.read()
elif url_scheme in ('http', 'https'):
    data = urlopen(path).read()
else:
    raise ThriftParserError('ThriftPy does not support generating module '
                            'with path in protocol \'{}\''.format(
                                url_scheme))
```
