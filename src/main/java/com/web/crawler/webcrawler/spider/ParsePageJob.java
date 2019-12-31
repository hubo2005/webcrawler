package com.web.crawler.webcrawler.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ParsePageJob<T> implements Delayed {


    private final long delayTime;

    private T job;

    private List<T> jobs;

    public ParsePageJob(T job) {
        this.delayTime = 0;
        this.job = job;
        this.jobs = new ArrayList<>();
        this.jobs.add(job);
    }

    public ParsePageJob(List<T> jobs) {
        this.delayTime = 0;
        this.jobs = jobs;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = delayTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    public List<T> getJobs() {
        return jobs;
    }

    public void setJobs(List<T> jobs) {
        this.jobs = jobs;
    }
}

