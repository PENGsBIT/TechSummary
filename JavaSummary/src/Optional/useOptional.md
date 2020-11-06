# Blog-使用 Optional 处理 null
开头拿数据的时候可能会忘记写 if (Obj != null) —— 可能会导致后面一系列的判断都需要判断null NullPointerException

## Optional.ofNullable

```bash
/**
     * Returns an {@code Optional} describing the specified value, if non-null,
     * otherwise returns an empty {@code Optional}.
     *
*/
```
##Optional Stream

```java
public List<User> getUsers(Collection<Integer> userIds) {
    return userIds.Stream()
            .map(this::getUserById)    // 获得 Stream<Optional<User>>
            .flatMap(Optional::Stream) // Stream 的 flatMap 方法将多个流合成一个流
            .collect(Collectors.toList());
}
```