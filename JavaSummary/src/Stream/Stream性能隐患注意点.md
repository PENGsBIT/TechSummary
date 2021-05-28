# Blog-Stream性能隐患注意点
## 处理N+1查询
```
        /*************************************************************************
         * 【需求：查询所有Wiki站点下的全部词条，并打印所有词条及其所属Wiki站点】
         *************************************************************************/
        
        List<Wiki> wikis = dependenceWikiDomainService.selectByExample(
                Example.builder(Wiki.class).andWhere(
                        Sqls.custom().andIn("id", Arrays.asList(58L, 49L, 76L, 91L, 81L))
                ).build()
        );

        /******************************** old  ********************************/
        long to1 = Instant.now().toEpochMilli();
        wikis.forEach(wiki -> {
            List<WikiPage> wikiPages = dependenceWikiPageDomainService.select(
                    new WikiPage().setWikiId(wiki.getId())
            );
            wikiPages.forEach(wikiPage -> {
                // System.out.println(
                //     String.format("%s, page from ==> %s", wikiPage.getName(), wiki.getName())
                // );
            });
        });
        System.out.println("old method complete in ==> {}", Instant.now().toEpochMilli() - to1);
        // old method complete in ==> 627

        /******************************** new  ********************************/
        long tn1 = Instant.now().toEpochMilli();
        // 批量查询所有词条
        List<Long> wikiIds = wikis.stream().map(Wiki::getId).collect(Collectors.toList());
        List<WikiPage> wikiPages = dependenceWikiPageDomainService.selectByExample(
                Example.builder(WikiPage.class).andWhere(Sqls.custom().andIn("id", wikiIds)).build()
        );

        // 构造Wiki站点映射集
        Map<Long, Wiki> wikiMap = wikis.stream().collect(
                Collectors.toMap(Wiki::getId, Function.identity())
        );

        // 根据Wiki站点映射集查找词条所属站点
        wikiPages.forEach(wikiPage -> {
            Wiki wiki = wikiMap.getOrDefault(wikiPage.getWikiId(), new Wiki().setName("-"));
            // System.out.println(
            //     String.format("%s, page from ==> %s", wikiPage.getName(), wiki.getName())
            // );
        });
        System.out.println("new method complete in ==> {}", Instant.now().toEpochMilli() - tn1);
        // new method complete in ==> 86
```
结果显示，老方法耗时627ms, 使用IN查询+映射集只需要86ms  

## 树形结构建立
```
/*************************************************************************
 * 【需求：将某篇文章的所有评论整理成树形结构（root 10个元素，2层树形）】
 *************************************************************************/
/******************************** old  ********************************/
long to2 = Instant.now().toEpochMilli();
// 查询一级评论
List<Comment> parentComments = dependenceCommentDomainService.selectByExample(
    Example.builder(Comment.class).andWhere(
        Sqls.custom().andEqualTo("sourceType", "Article").andEqualTo("sourceId", 53963L).andIsNull("childOfId")
    ).orderByDesc("createdAt").build()
);

// 遍历一级评论构造子评论列表
List<List<Comment>> resultOld = parentComments.stream().map(parentComment -> {
    List<Comment> childs = dependenceCommentDomainService.select(
        new Comment().setSourceType(parentComment.getSourceType())
            .setSourceId(parentComment.getSourceId()).setChildOfId(parentComment.getId())
    );
    // do something
    return childs;
}).collect(Collectors.toList());
log.info("old tree build in ==> {}", Instant.now().toEpochMilli() - to2);
// old tree build in ==> 1219

/******************************** new  ********************************/
long tn2 = Instant.now().toEpochMilli();
// 查询所有评论
List<Comment> comments = dependenceCommentDomainService.selectByExample(
    Example.builder(Comment.class).andWhere(
        Sqls.custom().andEqualTo("sourceType", "Article").andEqualTo("sourceId", 53963L)
    ).orderByDesc("createdAt").build()
);

// 根据评论父ID分组
Map<Long, List<Comment>> commentsMap = comments.stream().filter(
    e -> null != e.getChildOfId()
).collect(
    Collectors.groupingBy(Comment::getChildOfId, Collectors.toList())
);

// 筛选一级评论
List<Comment> rootComments = comments.stream().filter(
    e -> null == e.getChildOfId()
).collect(Collectors.toList());

// 遍历一级评论，查找映射集中的子评论列表
List<List<Comment>> resultNew = rootComments.stream().map(e -> {
    List<Comment> childs = commentsMap.getOrDefault(e.getId(), Collections.emptyList());
    // do something
    return childs;
}).collect(Collectors.toList());
log.info("new tree build in ==> {}", Instant.now().toEpochMilli() - tn2);
// new tree build in ==> 98
```
结果显示，老方法耗时1219ms, 使用新方法构造树只需要98ms