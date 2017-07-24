package com.seveniu.crawler.spider.pageProcessor;

import com.seveniu.entity.data.Link;
import com.seveniu.entity.data.Node;
import com.seveniu.entity.data.PageResult;
import com.seveniu.spider.parse.ParseResult;
import com.seveniu.template.PagesTemplate;
import com.seveniu.template.def.PageTemplate;
import com.seveniu.user.CrawlerUser;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import static com.seveniu.def.PageContext.CONTEXT_NODE;

/**
 * 多级内容页 大于1级
 * Created by seveniu on 5/12/16.
 * MyPageProcessor
 */
public class SinglePageProcessor extends MyPageProcessor {


    public SinglePageProcessor(PagesTemplate pagesTemplate, CrawlerUser consumer) {
        super(pagesTemplate, consumer);
    }

    @Override
    void process0(Page page, ParseResult parseResult) {
        singlePage(page, parseResult);
    }

    @Override
    protected PageTemplate getTemplate(Page page) {
        // 根据序号找到对应模板
        PageTemplate pageTemplate = pagesTemplate.getTemplate(0);
        if (pageTemplate == null) {
            logger.error("can't find template by serial num : {}", 0);
        }
        return pageTemplate;
    }


    private void singlePage(Page page, ParseResult parseResult) {
        String url = page.getUrl().get();
        // 处理解析的结果
        Node contextNode = (Node) page.getRequest().getExtra(CONTEXT_NODE);
        // 内容
        if (parseResult.getFieldResults() != null && parseResult.getFieldResults().size() > 0) {
            // 有文本字段,就获取 node 添加内容
            if (contextNode == null) {
                contextNode = new Node(url, taskId);
                statistic.addCreateNodeCount(1);
            }
            contextNode.addPageResult(new PageResult().setUrl(url).setFieldResults(parseResult.getFieldResults()));
        } else {
            logger.error("content page field is null, url : {}", url);
        }
        // 跳转链接
        if (parseResult.hasLinks()) {
            for (Link targetLink : parseResult.getTargetLinks()) {
                if (consumer.getClient().has(targetLink.getUrl())) { //
                    logger.debug("url is repeat : {} ", targetLink);
                    statistic.addRepeatUrlCount(1);
                } else {
                    page.addTargetRequest(new Request(targetLink.getUrl()));
                }
            }
            // 统计
            statistic.addCreateUrlCount(parseResult.getTargetLinks().size());
            statistic.addTargetUrlCount(parseResult.getTargetLinks().size());
        }
        // 下一页链接
        if (parseResult.hasNextPageLinks()) {
            for (Link next : parseResult.getNextPageLinks()) {
                page.addTargetRequest(new Request(next.getUrl()).putExtra(CONTEXT_NODE, contextNode));
            }
            // 统计
            statistic.addCreateUrlCount(parseResult.getNextPageLinks().size());
        } else {
            statistic.addDoneNodeCount(1);
            page.putField(CONTEXT_NODE, contextNode);
//            page.putField(TASK, task);
        }
    }

}
