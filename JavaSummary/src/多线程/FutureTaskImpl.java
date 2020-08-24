package 多线程;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

//FutureTask一个可取消的异步计算，FutureTask 实现了Future的基本方法，提空 start cancel 操作，可以查询计算是否已经完成，
// 并且可以获取计算的结果。结果只可以在计算完成之后获取，get方法会阻塞当计算没有完成的时候，一旦计算已经完成，那么计算就不能再次启动或是取消。
//一个FutureTask 可以用来包装一个 Callable 或是一个runnable对象。因为FurtureTask实现了Runnable方法，
// 所以一个 FutureTask可以提交(submit)给一个Excutor执行(excution).
public class FutureTaskImpl {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 15, 15L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),new ThreadPoolExecutor.AbortPolicy());

        Random r=new Random();
        int count=1;
        while (count <= 5) {
            ArrayList<Integer> list=new ArrayList<>();
            for (int i = 1; i <=20; i++) {
                list.add(r.nextInt(i));
            }
            String taskName="Task:"+count;
            ComputeTask task = new ComputeTask(list, 10,taskName);
            Future<ArrayList<Integer>> result = executorService.submit(task);

            Thread.sleep(1000);
            // 可以取消异步任务
            // future.cancel(true);
            System.out.println(task.getTaskName()+":"+ list+" have TOPK:"+result.get());

            count++;
        }
        System.out.println("===========Thread End===========");

    }

}


class ComputeTask implements Callable {
    private ArrayList<Integer> nums;
    private Integer K;
    private String taskName;

    public ComputeTask(ArrayList<Integer> nums, Integer k, String taskName) {
        this.nums = nums;
        K = k;
        this.taskName = taskName;
    }

    public ArrayList<Integer> getTopK(ArrayList<Integer> nums,int K) {
        if (nums.size() <= K) {
            return nums;
        }
        ArrayList<Integer> result = new ArrayList<>();
        Random random=new Random();
        int index = random.nextInt(nums.size());
        ArrayList<Integer> rest = new ArrayList<>();
        ArrayList<Integer> equals = new ArrayList<>();
        for (Integer num : nums) {
            if (num > nums.get(index)) {
                result.add(num);
            } else if (num == nums.get(index)) {
                equals.add(num);
            } else {
                rest.add(num);
            }
        }
        if (result.size() == K) {
            return result;
        } else if (result.size() > K) {
            return getTopK(result, K);
        } else if (result.size()+equals.size()>=K) {
            int r=K - result.size();
            result.addAll(equals.subList(0, r));
            return result;
        }
        else {
            result.addAll(getTopK(rest, K - result.size()));
            return result;
        }
    }

    public String getTaskName() {
        return this.taskName;
    }

    @Override
    public ArrayList<Integer> call() throws Exception {
        return getTopK(this.nums, this.K);
    }
}
