package com.example.board.config.redis;

public class CacheKey {
    public static final int DEFAULT_EXPIRE_SEC = 60; // 1 minutes
    public static final int DAILY_BEST_EXPIRE_SEC = 60 * 5; // 5 minutes
    public static final String DAILY_BEST = "dailyBest";
    public static final int WEEKLY_BEST_EXPIRE_SEC = 60 * 10; // 10 minutes
    public static final String WEEKLY_BEST = "weeklyBest";
}
