package com.info.back.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: chenjunqi
 * @Date: 2018/6/28
 * @Description:
 */
@Slf4j
public class ThreadPoolShuntUtil {

    public static <T extends Runnable> void shunt(List<T> list) {
        shunt(16, 128, list);
    }

    /**
     * @param corePoolSize 工作线程的数量
     * @param shuntSize    缓冲区大小
     * @param list         任务实例
     */
    public static <T extends Runnable> void shunt(int corePoolSize, int shuntSize, List<T> list) {
        LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<T> cache = new LinkedBlockingQueue<>();

        if (list != null && list.size() > 0) {
            for (T callable : list) {
                queue.offer(callable);
            }
        }

        ExecutorService service = Executors.newFixedThreadPool(corePoolSize);
        while (!queue.isEmpty()) {

            while (true) {
                T tmp = queue.poll();
                if (tmp != null) {
                    cache.offer(tmp);
                }
                if (queue.isEmpty() || cache.size() == shuntSize) {
                    break;
                }
            }

            while (!cache.isEmpty()) {
                T callable = cache.poll();
                service.submit(callable);
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.error("shunt error:{}",e);
            }
        }
        service.shutdown();
    }
}
