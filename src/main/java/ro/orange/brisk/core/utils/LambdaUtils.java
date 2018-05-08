package ro.orange.brisk.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.*;
import java.util.function.Function;

public class LambdaUtils {

    static Logger logger = LoggerFactory.getLogger(LambdaUtils.class);

    public static <T, R> Function<T, R> runtimeLambda(Class<T> baseClazz, String method, Class<R> returnClazz) {
        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType getter = MethodType.methodType(returnClazz);
        MethodHandle target = null;
        try {
            target = caller.findVirtual(baseClazz, method, getter);
        } catch (Exception e) {
            logger.error("[runtimeLambda] Eroare findVirtual method {}", method, e);
            throw new RuntimeException(e);
        }
        MethodType func = target.type();
        CallSite site = null;
        try {
            site = LambdaMetafactory.metafactory(caller,
                    "apply",
                    MethodType.methodType(Function.class),
                    func.generic(), target, func);
        } catch (Exception e) {
            logger.error("[runtimeLambda] Eroare metafactory", e);
            throw new RuntimeException(e);
        }

        MethodHandle factory = site.getTarget();

        try {
            return (Function<T, R>) factory.invoke();
        } catch (Throwable e) {
            logger.error("[runtimeLambda] Eroare throwable", e);
            throw new RuntimeException(e);
        }
    }

}
