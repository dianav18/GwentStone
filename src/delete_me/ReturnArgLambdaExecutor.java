package delete_me;

/**
 * The interface Return arg lambda executor.
 *
 * @param <R> the type parameter
 * @param <A> the type parameter
 */
public interface ReturnArgLambdaExecutor<R, A> {
    /**
     * Execute r.
     *
     * @param a the a
     * @return the r
     */
    R execute(A a);
    }