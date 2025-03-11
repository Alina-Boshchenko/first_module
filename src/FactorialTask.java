
import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {

    private Long start;
    private Long end;


    public FactorialTask(Long end) {
        start = 1L;
        this.end = end;
    }

    public FactorialTask(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= 2L) {
            Long multiply = 1L;
            for (Long i = start; i <= end; i++) {
                multiply *= i;
            }
            return multiply;
        } else {
            Long middle = (start + end) / 2;
            FactorialTask leftTask = new FactorialTask(start, middle);
            FactorialTask rightTask = new FactorialTask(middle+1, end);

            leftTask.fork();
            rightTask.fork();

            return leftTask.join() * rightTask.join();
        }
    }

}
