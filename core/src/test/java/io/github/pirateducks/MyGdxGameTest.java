package io.github.pirateducks;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.HashMap;
import java.util.Map;

public class MyGdxGameTest extends BlockJUnit4ClassRunner implements ApplicationListener {

    private final Map<FrameworkMethod, RunNotifier> test_instance = new HashMap<FrameworkMethod, RunNotifier>();

    /**
     * @param klass refers to the class of the test being tested if @RunWith is used
     *
     * @throws InitializationError if the file isn't able to run
     */
    public MyGdxGameTest(Class<?> klass) throws InitializationError {
        super(klass);

        /*
         * this refers to the test
         * conf defines how many updates per second (60 by default)
         *     and the maximum number of threads for requests
         */
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(this, conf);
    }
    
    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void render() {
        /**
         * Runs the function runChild for each test
         * val.getKey() represents the specific test
         * val.getValue() represents whether the specific test has passed or not
         */
        for (Map.Entry<FrameworkMethod, RunNotifier> val : test_instance.entrySet()) {
            super.runChild(val.getKey(), val.getValue());
        }
        // Clears the HashMap
        test_instance.clear();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    /**
     * @param method refers to the test
     * @param notifier refers to whether it's passes the tests or not
     */
    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {

        /*
         * the synchronized function prevents thread interference
         * Adds every instance being tested to the HashMap
         */
        synchronized(test_instance){
            test_instance.put(method, notifier);
        }

        //wait until that test is invoked and continues only if the execution was successful
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

}
