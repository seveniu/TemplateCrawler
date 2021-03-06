package com.seveniu.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by seveniu on 7/22/17.
 * url 对应的 content
 * 线程不安全
 * *
 */
public class DataContent {
    private boolean error; // 标记是否失败

    public DataContent(String url) {
        this.url = url;
    }

    private String url;
    private Map<Integer, PageContent> pageContents;
    private List<DataContent> children;
    @Transient
    @JsonIgnore
    private DataContent parent;
    private AtomicInteger allChildrenCount = new AtomicInteger(0);
    private AtomicInteger doneChildrenCount = new AtomicInteger(0);

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<PageContent> getPageContents() {
        return pageContents.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public List<DataContent> getChildren() {
        return children;
    }

    public void setChildren(List<DataContent> children) {
        this.children = children;
    }

    public boolean isError() {
        return error;
    }

    public synchronized void addChild(DataContent child) {
        if (this.children == null) {
            this.children = new LinkedList<>();
        }
        this.children.add(child);
        child.parent = this;
        this.allChildrenCount.getAndIncrement();
    }

    public synchronized DataContent addPage(PageContent pageContent, int index) {
        if (this.pageContents == null) {
            this.pageContents = new HashMap<>();
        }
        this.pageContents.put(index, pageContent);
        return this;
    }

    /**
     * 递归向上遍历父节点，判断是否完成. 没有完成 返回 null；直到根节点都完成，则返回 根节点
     */
    public synchronized DataContent error() {
        this.error = true;
        if (this.allChildrenCount.get() == this.doneChildrenCount.get()) {
            if (this.parent == null) {
                return this;
            } else {
                return this.parent.error();
            }
        }
        return null;
    }

    /**
     * 递归向上遍历父节点，判断是否完成. 没有完成 返回 null；直到根节点都完成，则返回 根节点
     */
    public synchronized DataContent done() {
        if (this.allChildrenCount.get() == this.doneChildrenCount.get()) {
            if (this.parent == null) {
                return this;
            } else {
                this.parent.doneChildrenCount.getAndIncrement();
                return this.parent.done();
            }
        }
        return null;
    }

}
