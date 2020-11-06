package Stream;

/**
 * @Program: JavaSummary
 * @Author: zhoupengcheng03
 * @Package: Stream
 * @ClassName: WikiPage
 * @Description:
 * @Create: 2020-11-06 16:38
 **/
public class WikiPage {


    private long id;
    private long wikiId;

    public WikiPage setId(long id) {
        this.id = id;
        return this;
    }

    public WikiPage setWikiId(long wikiId) {
        this.wikiId = wikiId;
        return this;
    }

    public long getId() {
        return this.id;
    }

    public long getWikiId() {
        return this.wikiId;
    }

}
