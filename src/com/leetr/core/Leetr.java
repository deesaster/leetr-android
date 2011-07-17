package com.leetr.core;

import android.content.Context;
import com.leetr.core.interfaces.ILeetrUtility;
import com.leetr.core.utility.FileCacheUtility;
import com.leetr.core.utility.LocationUpdaterUtility;
import com.leetr.core.utility.SimpleHttpUtility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-04
 * Time: 9:43 PM
 * <p/>
 */
public final class Leetr {
    public static final int UTILITY_SIMPLE_HTTP = 0;
    public static final int UTILITY_FILE_CACHE = 1;
    public static final int UTILITY_LOCATION_UPDATER = 2;

    private static Leetr instance;

    private Context mContext;
    private Class<?>[] mServiceClass;
    private HashMap<Class<?>, ILeetrUtility> mServiceMap;

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new Leetr(context);
        }
    }

    public static Context getContext() {
        if (instance == null) {
            throw new RuntimeException("Leetr has not been initialized. Please call Leetr.initialize before asking for context");
        }

        return instance.getLeetrContext();
    }

    public static void terminate() {
        instance.shutdownServices();
        instance = null;
    }

    public static ILeetrUtility getUtility(int service) {
        return instance.getUtilitySingleton(service);
    }

    private Leetr(Context context) {
        super();

        mContext = context;

        Settings.initialize(context);
        initUtilities();
    }

    public Context getLeetrContext() {
        return mContext;
    }

    private void initUtilities() {
        mServiceMap = new HashMap<Class<?>, ILeetrUtility>();

        mServiceClass = new Class<?>[]{
                SimpleHttpUtility.class,
                FileCacheUtility.class,
                LocationUpdaterUtility.class};
    }

    private void shutdownServices() {
        if (mServiceMap != null) {
            for (ILeetrUtility utility : mServiceMap.values()) {
                utility.shutdown();
            }
        }
    }

    private ILeetrUtility getUtilitySingleton(int service) {
        if (service < mServiceClass.length) {
            Class<?> serviceClass = mServiceClass[service];
            ILeetrUtility s = null;

            if (mServiceMap.containsKey(serviceClass)) {
                s = mServiceMap.get(serviceClass);
            } else {
                Constructor[] ctors = serviceClass.getDeclaredConstructors();


                for (Constructor c : ctors) {
                    // TODO fix constructor checking, don't instanciate the first available constructor
                    Class<?>[] paramTypes = c.getParameterTypes();

                    if (paramTypes.length == 1 && paramTypes[0] == Context.class) {
                        try {
                            s = (ILeetrUtility) c.newInstance(mContext);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else if (paramTypes.length == 0) {
                        try {
                            s = (ILeetrUtility) c.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            if (s != null) {
                mServiceMap.put(serviceClass, s);
                s.start();
            }

            return s;
        }

        return null;
    }
}
