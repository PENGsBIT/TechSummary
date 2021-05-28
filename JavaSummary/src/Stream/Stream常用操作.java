package Stream;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Program: JavaSummary
 * @Author: zhoupengcheng03
 * @Package: stream
 * @ClassName: JavaStream
 * @Description:
 * @Create: 2020-11-06 16:36
 **/
public class Stream常用操作 {
    public static void main(String[] args) {
        List<WikiPage> list = Arrays.asList(
                new WikiPage().setId(1L).setWikiId(1L),
                new WikiPage().setId(2L).setWikiId(1L),
                new WikiPage().setId(3L).setWikiId(2L),
                new WikiPage().setId(4L).setWikiId(2L),
                new WikiPage().setId(5L).setWikiId(2L),
                new WikiPage().setId(6L).setWikiId(2L)
        );
        // 1. map(), 维度不变, 一一映射
        // List<Long> idList = list.stream().map(e -> {
        //     return e.getId();
        // }).collect(Collectors.toList());
        List<Long> idList = list.stream().map(WikiPage::getId).collect(Collectors.toList());


        // 2. reduce(), 降维处理, 允许默认值
        Integer result = Arrays.stream(
                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}
        ).reduce(0, Integer::sum);

        // 3. filter(), 过滤器
        // List<WikiPage> wikiPages = list.stream().filter(e -> {
        //     return e.getId() >= 3L;
        // }).collect(Collectors.toList());
        List<WikiPage> wikiPages = list.stream().filter(
                e -> e.getId() >= 3L
        ).collect(Collectors.toList());

        // 4. limit(), 限制流数量
        List<WikiPage> limitWikiPages = list.stream().limit(3).collect(Collectors.toList());

        // 5. count(), 计数
        long count = list.stream().filter(Objects::nonNull).count();

        // 6. sort(), 排序, 可自定义比较器
        List<Integer> sortList = Arrays.stream(
                new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1}
        ).sorted().collect(Collectors.toList());
    }
}
