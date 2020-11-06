package Stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Program: JavaSummary
 * @Author: zhoupengcheng03
 * @Package: Stream
 * @ClassName: Stream常用到的收集器
 * @Description:
 * @Create: 2020-11-06 17:51
 **/
public class Stream常用收集器 {
    public static void main(String[] args) {
         List<WikiPage> list = Arrays.asList(
                new WikiPage().setId(1L).setWikiId(1L),
                new WikiPage().setId(2L).setWikiId(1L),
                new WikiPage().setId(3L).setWikiId(2L),
                new WikiPage().setId(4L).setWikiId(2L),
                new WikiPage().setId(5L).setWikiId(2L),
                new WikiPage().setId(6L).setWikiId(2L)
        );
        /**收集器**/
        // 1. toList()
        // 2. groupingBy()
        Map<Long, List<WikiPage>> wikisMap = list.stream().collect(
                Collectors.groupingBy(WikiPage::getWikiId, Collectors.toList())
        );

        // 3. toMap()
        Map<Long, WikiPage> wikiPageMap = list.stream().collect(
                Collectors.toMap(WikiPage::getId, Function.identity())
        );

        // 4. toSet()
        Set<String> set = Arrays.stream(
                new String[]{"Java", "Python", "C", "Java"}
        ).collect(Collectors.toSet());

        // 5. joining()
        String join = list.stream().map(
                e ->  String.valueOf(e.getId())
        ).collect(Collectors.joining(","));
    }
}
